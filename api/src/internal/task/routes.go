package task

import (
	"api/src/middleware"

	"github.com/gin-gonic/gin"
)

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/tasks", middleware.AuthRequired())
	rg.POST("/tasks", middleware.AuthRequired())
}
