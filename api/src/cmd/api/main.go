package main

import (
	"api/src/internal/database"
	"api/src/internal/task"

	"github.com/gin-gonic/gin"
)

func main() {
	db := database.Connect("app.db")

	err := db.AutoMigrate(
		&task.Task{},
	)
	if err != nil {
		panic(err)
	}

	router := gin.Default()
	router.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	router.Run()
}
