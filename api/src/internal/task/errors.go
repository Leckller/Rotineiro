package task

import "errors"

var (
	ErrTaskNotFound      = errors.New("Task not found")
	ErrTaskBadRequest    = errors.New("Invalid task request")
	ErrTaskAlreadyExists = errors.New("Task already exists")
)
