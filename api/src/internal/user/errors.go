package user

import "errors"

var (
	ErrUserNotFound       = errors.New("User not found")
	ErrEmailAlreadyExists = errors.New("Email already exists")
	ErrInvalidCredentials = errors.New("Invalid credentials")
)
