package routine

import (
	"api/src/internal/task"

	"gorm.io/gorm"
)

func Wird(db *gorm.DB, taskService task.Service) (*Handler, Service) {

	repository := NewRepository(db)
	service := NewService(repository, taskService)
	handler := NewHandler(service)
	return &handler, service

}
