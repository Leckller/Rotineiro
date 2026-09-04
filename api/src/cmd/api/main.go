package main

import (
	"api/src/internal/database"
	"api/src/internal/history"
	"api/src/internal/routine"
	"api/src/internal/task"
	"api/src/internal/user"
	"api/src/utils"

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

	{
		v1 := router.Group("/v1")
		user.RegisterRoutes(v1, *user.Wird(db))
		task.RegisterRoutes(v1, *task.Wird(db))
	}

	router.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	router.Run()
}
