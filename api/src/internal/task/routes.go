package task

import (
	"github.com/gin-gonic/gin"
)

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/tasks", h.FindAllByUser)
	rg.POST("/tasks", h.Create)
}
