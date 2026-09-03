package history

import "github.com/gin-gonic/gin"

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.GET("/history")
	rg.GET("/history/task")
	rg.GET("/history/task/:id")
	rg.GET("/history/routine")
	rg.GET("/history/routine/:id")
}
