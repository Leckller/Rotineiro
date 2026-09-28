package routine

import "gorm.io/gorm"

func Wird(db *gorm.DB) *Handler {

	repository := NewRepository(db)
	service := NewService(repository)
	handler := NewHandler(service)
	return &handler

}
