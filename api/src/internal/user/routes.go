package user

import "github.com/gin-gonic/gin"

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.POST("/login")
	rg.POST("/register")
}
