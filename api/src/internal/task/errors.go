package task

import "errors"

var (
	ErrTaskNotFound      = errors.New("Task not found")
	ErrTaskBadRequest    = errors.New("Invalid task request")
	ErrTaskUnauthorized  = errors.New("Unauthorized request")
	ErrTaskAlreadyExists = errors.New("Task already exists")
)
