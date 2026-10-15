package main

import (
	"api/src/internal/database"
	"api/src/internal/routine"
	"api/src/internal/task"
	"api/src/internal/user"
	"api/src/middleware"
	"api/src/utils"
	"time"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

func main() {
	utils.RegisterJSONTagNames()

	db := database.Connect("app.db")

	err := db.AutoMigrate(
		&user.User{},
		&task.Task{},
		&task.TaskEvent{},
		&routine.Routine{},
		&routine.RoutineHistory{},
	)

	if err != nil {
		panic(err)
	}

	router := gin.Default()
	router.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"*"},
		AllowMethods:     []string{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Content-Type", "Authorization"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: false,
		AllowOriginFunc: func(origin string) bool {
			return origin == "https://github.com"
		},
		MaxAge: 12 * time.Hour,
	}))

	v1 := router.Group("/v1")
	v1.Use(middleware.Logger())

	user.RegisterRoutes(v1, *user.Wird(db))

	auth := v1.Group("")
	auth.Use(middleware.AuthRequired())

	taskHandler, taskService := task.Wird(db)
	task.RegisterRoutes(auth, *taskHandler)

	routineHandler, _ := routine.Wird(db, taskService)
	routine.RegisterRoutes(auth, *routineHandler)

	router.GET("/ping", utils.Ping)

	router.Run()
}
