package main

import (
	"api/src/internal/database"
	"api/src/internal/history"
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
		&task.Task{},
		&user.User{},
		&history.History{},
		&routine.Routine{},
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

	task.RegisterRoutes(auth, *task.Wird(db))
	routine.RegisterRoutes(auth, *routine.Wird(db))

	router.GET("/ping", utils.Ping)

	router.Run()
}
