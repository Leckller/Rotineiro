package user

import (
	"api/src/utils"
	"net/http"

	"github.com/gin-gonic/gin"
)

type Handler interface {
	Create(ctx *gin.Context)
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
		if fieldErrors := utils.FormatValidationErrors(err); fieldErrors != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"errors": fieldErrors})
			return
		}
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	err := h.service.Create(&User{
		Name:     user.Name,
		Email:    user.Email,
		Password: user.Password,
	})

	if err != nil {
		ctx.JSON(400, gin.H{"error": err.Error()})
		return
	}

	ctx.JSON(201, gin.H{"message": "User created successfully"})

}
