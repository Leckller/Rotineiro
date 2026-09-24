package user

import (
	"api/src/utils"
	"errors"
	"io"
	"net/http"

	"github.com/gin-gonic/gin"
)

type Handler interface {
	Create(ctx *gin.Context)
	Login(ctx *gin.Context)
}

type handler struct {
	service Service
}

func NewHandler(service Service) Handler {
	return &handler{
		service: service,
	}
}

func (h *handler) Create(ctx *gin.Context) {

	var user CreateUser

	if err := ctx.ShouldBindBodyWithJSON(&user); err != nil {

		if errors.Is(err, io.EOF) {
			ctx.JSON(http.StatusBadRequest, gin.H{
				"error": "Body is required",
			})
			return
		}

		if fieldErrors := utils.FormatValidationErrors(err); fieldErrors != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"errors": fieldErrors})
			return
		}
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return

	}

	token, err := h.service.Create(&User{
		Name:     user.Name,
		Email:    user.Email,
		Password: user.Password,
	})

	if err != nil {
		ctx.JSON(400, gin.H{"error": err.Error()})
		return
	}

	ctx.JSON(201, gin.H{"token": token, "message": "User created successfully"})

}

func (h *handler) Login(ctx *gin.Context) {

	var user LoginDTO

	if err := ctx.ShouldBindBodyWithJSON(&user); err != nil {

		if errors.Is(err, io.EOF) {
			ctx.JSON(http.StatusBadRequest, gin.H{
				"error": "Body is required",
			})
			return
		}

		if fieldErrors := utils.FormatValidationErrors(err); fieldErrors != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"errors": fieldErrors})
			return
		}
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return

	}

	token, err := h.service.Login(user.Email, user.Password)

	if err != nil {
		ctx.JSON(400, gin.H{"error": err.Error()})
		return
	}

	ctx.JSON(200, gin.H{"token": token, "message": "User login successfully"})

}
