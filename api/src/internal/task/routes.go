package task

import (
	"github.com/gin-gonic/gin"
)

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/tasks", h.FindAllByUser)
	rg.POST("/tasks", h.Create)
	rg.PATCH("/tasks", h.Update)
	rg.DELETE("/tasks/:taskID", h.Delete)
	rg.PATCH("/tasks/complete/:taskID", h.CreateEvent(TaskEventCompleted))
	rg.PATCH("/tasks/start/:taskID", h.CreateEvent(TaskEventStart))
	rg.PATCH("/tasks/pause/:taskID", h.CreateEvent(TaskEventPause))
	rg.PATCH("/tasks/skip/:taskID", h.CreateEvent(TaskEventSkip))
}
