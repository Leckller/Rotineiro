package routine

import (
	"api/src/utils"
	"errors"
	"io"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type Handler interface {
	FindAllByUser(ctx *gin.Context)
	Create(ctx *gin.Context)
	Update(ctx *gin.Context)
	Delete(ctx *gin.Context)
	StartRoutine(ctx *gin.Context)
	CompleteRoutine(ctx *gin.Context)
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

	var createRoutineDTO CreateRoutineDTO

	if err := ctx.ShouldBindBodyWithJSON(&createRoutineDTO); err != nil {

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

	routineID, err := h.service.Create(userID.(uint), createRoutineDTO)

	if err != nil {

		if errors.Is(err, ErrRoutineAlreadyExists) {
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

	ctx.JSON(http.StatusCreated, gin.H{"routineId": routineID, "message": "Routine created successfully"})

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

	routines, paginationMeta, err := h.service.FindAllByUser(userID.(uint), page, pageSize)

	if err != nil {

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return

	}

	ctx.JSON(http.StatusOK, gin.H{
		"data": routines,
		"meta": paginationMeta,
	})

}

func (h *handler) Update(ctx *gin.Context) {

	var updateDTO UpdateRoutineDTO

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

		if errors.Is(err, ErrRoutineBadRequest) {
			ctx.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
			return
		}

		if errors.Is(err, ErrRoutineAlreadyExists) {
			ctx.JSON(http.StatusConflict, gin.H{
				"error": err.Error(),
			})
			return
		}

		if errors.Is(err, ErrRoutineNotFound) {
			ctx.JSON(http.StatusNotFound, gin.H{
				"error": err.Error(),
			})
			return
		}

		print(err.Error())

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	ctx.Status(http.StatusNoContent)

}

func (h *handler) Delete(ctx *gin.Context) {

	routineID, err := strconv.Atoi(ctx.Param("routineID"))

	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": ErrRoutineBadRequest})
		return
	}

	userID := ctx.GetUint("userID")

	err = h.service.Delete(userID, uint(routineID))

	if err != nil {
		if errors.Is(err, ErrRoutineNotFound) {
			ctx.JSON(http.StatusNotFound, gin.H{
				"error": err.Error(),
			})
			return
		}

		print(err.Error())
		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	ctx.Status(http.StatusNoContent)

}

func (h *handler) StartRoutine(ctx *gin.Context) {

	routineID, err := strconv.Atoi(ctx.Param("routineID"))

	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": ErrRoutineBadRequest})
		return
	}

	userID := ctx.GetUint("userID")

	err = h.service.StartRotuine(userID, uint(routineID))

	if err != nil {
		if errors.Is(err, ErrRoutineNotFound) {
			ctx.JSON(http.StatusNotFound, gin.H{
				"error": err.Error(),
			})
			return
		}
		print(err.Error())

		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": "internal server error",
		})
		return
	}

	ctx.Status(http.StatusNoContent)

}

func (h *handler) CompleteRoutine(ctx *gin.Context) {

	routineID, err := strconv.Atoi(ctx.Param("routineID"))

	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": ErrRoutineBadRequest})
		return
	}

	userID := ctx.GetUint("userID")

	err = h.service.CompleteRotuine(userID, uint(routineID))

	if err != nil {
		if errors.Is(err, ErrRoutineNotFound) {
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
