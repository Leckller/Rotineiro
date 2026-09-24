package task

import (
	"api/src/utils"
	"errors"
	"io"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type Handler interface {
	Create(ctx *gin.Context)
	FindAllByUser(ctx *gin.Context)
	Update(ctx *gin.Context)
	StartTask(ctx *gin.Context)
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

	userID, exists := ctx.Get("userID")

	if !exists {
		ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Não autorizado"})
		return
	}

	userId, err := h.service.Create(userID.(uint), createTaskDTO)

	if err != nil {

		if errors.Is(err, ErrTaskAlreadyExists) {
			ctx.JSON(http.StatusConflict, gin.H{
				"error": err.Error(),
			})
			return
		}

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return

	}

	ctx.JSON(http.StatusCreated, gin.H{"taskId": userId, "message": "Task created successfully"})

}

func (h *handler) FindAllByUser(ctx *gin.Context) {

	page, err := strconv.Atoi(ctx.DefaultQuery("page", "1"))

	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	pageSize, err := strconv.Atoi(ctx.DefaultQuery("pageSize", "10"))

	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	userID, exists := ctx.Get("userID")

	if !exists {
		ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Não autorizado"})
		return
	}

	tasks, paginationMeta, err := h.service.FindAllByUser(userID.(uint), page, pageSize)

	if err != nil {

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return

	}

	ctx.JSON(http.StatusOK, gin.H{
		"data": tasks,
		"meta": paginationMeta,
	})

}

func (h *handler) Update(ctx *gin.Context) {

	var updateDTO UpdateTaskDTO

	if err := ctx.ShouldBindBodyWithJSON(&updateDTO); err != nil {

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

	userID, exists := ctx.Get("userID")

	if !exists {
		ctx.JSON(http.StatusUnauthorized, gin.H{"error": "Não autorizado"})
		return
	}

	if err := h.service.Update(userID.(uint), updateDTO); err != nil {

		if errors.Is(err, ErrTaskBadRequest) {
			ctx.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
			return
		}

		if errors.Is(err, ErrTaskAlreadyExists) {
			ctx.JSON(http.StatusConflict, gin.H{
				"error": err.Error(),
			})
			return
		}

		if errors.Is(err, ErrTaskNotFound) {
			ctx.JSON(http.StatusNotFound, gin.H{
				"error": err.Error(),
			})
			return
		}

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	ctx.Status(http.StatusNoContent)

}

func (h *handler) StartTask(ctx *gin.Context) {

	taskID, err := strconv.Atoi(ctx.Param("taskID"))

	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": ErrTaskBadRequest})
		return
	}

	userID := ctx.GetUint("userID")

	err = h.service.StartTask(userID, uint(taskID))

	if err != nil {
		if errors.Is(err, ErrTaskNotFound) {
			ctx.JSON(http.StatusNotFound, gin.H{
				"error": err.Error(),
			})
			return
		}

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	ctx.Status(http.StatusNoContent)

}
