package user

import "gorm.io/gorm"

func Wird(db *gorm.DB) *Handler {

	userRepository := NewRepository(db)
	userService := NewService(userRepository)
	userHandler := NewHandler(userService)

	return &userHandler

}
