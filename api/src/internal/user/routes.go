package user

import "github.com/gin-gonic/gin"

func RegisterRoutes(rg *gin.RouterGroup, h Handler) {
	rg.POST("/login", h.Login)
	rg.POST("/register", h.Create)
}
