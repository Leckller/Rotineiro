package middleware

import (
	"api/src/utils"
	"net/http"

	"github.com/gin-gonic/gin"
)

func AuthRequired() gin.HandlerFunc {
	return func(ctx *gin.Context) {

		authorization := ctx.Request.Header.Get("Authorization")

		if authorization == "" {
			ctx.JSON(http.StatusUnauthorized, gin.H{
				"message": "não autorizado",
			})
			return
		}

		email, err := utils.VerifyToken(authorization)

		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{
				"message": "token inválido",
			})
			return
		}

		ctx.Set("email", email)

		ctx.Next()
	}
}
