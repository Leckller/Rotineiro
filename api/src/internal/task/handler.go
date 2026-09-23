package task

import (
	"api/src/utils"
	"errors"
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

	var createTaskDTO CreateTaskDTO

	if err := ctx.ShouldBindBodyWithJSON(&createTaskDTO); err != nil {
		if fieldErrors := utils.FormatValidationErrors(err); fieldErrors != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"errors": fieldErrors})
			return
		}

		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}

	if err := h.service.Create(createTaskDTO); err != nil {

		if errors.Is(err, ErrTaskAlreadyExists) {
			ctx.JSON(http.StatusConflict, gin.H{
				"error": err.Error(),
			})
		}

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})

	}

}
