package task

import "gorm.io/gorm"

func Wird(db *gorm.DB) *Handler {

	taskRepository := NewRepository(db)
	taskService := NewService(taskRepository)
	taskHandler := NewHandler(taskService)

	return &taskHandler

}
