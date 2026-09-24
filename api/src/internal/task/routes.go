package task

import (
	"github.com/gin-gonic/gin"
)

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/tasks", h.FindAllByUser)
	rg.POST("/tasks", h.Create)
	rg.PATCH("/tasks", h.Update)
	rg.PATCH("/tasks/start/:taskID", h.StartTask)
	rg.PATCH("/tasks/complete/:taskID", h.CompleteTask)
	rg.DELETE("/tasks/delete/:taskID", h.Delete)
}
