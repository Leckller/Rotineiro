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
			ctx.Abort()
			return
		}

		userID, err := utils.VerifyToken(authorization)

		if err != nil {
			ctx.JSON(http.StatusUnauthorized, gin.H{
				"message": "token inválido",
			})
			print(err.Error())
			ctx.Abort()
			return
		}

		ctx.Set("userID", *userID)
	}
}
