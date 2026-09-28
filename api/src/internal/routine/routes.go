package routine

import "github.com/gin-gonic/gin"

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/routines", h.FindAllByUser)
	rg.POST("/routines", h.Create)
	rg.PATCH("/routines", h.Update)
	rg.PATCH("/routines/start/:routineID", h.StartRoutine)
	rg.PATCH("/routines/complete/:routineID", h.CompleteRoutine)
	rg.DELETE("/routines/delete/:routineID", h.Delete)
}
