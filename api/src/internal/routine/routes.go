package routine

import "github.com/gin-gonic/gin"

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {

	rg.POST("/routines", h.Create)
	rg.PATCH("/routines", h.Update)
	rg.DELETE("/routines/:routineID", h.Delete)
	rg.GET("/routines", h.FindAllByUser)
	rg.GET("/routines/:routineID", h.FindByID)

	rg.PATCH("/routines/associate/:routineID/:taskID", h.AddTaskToRoutine)
	rg.DELETE("/routines/disassociate/:routineID/:taskID", h.DeleteTaskFromRoutine)
	rg.PATCH("/routines/start/:routineID", h.StartRoutine)
	rg.PATCH("/routines/finish/:routineID", h.FinishRoutine)

}
