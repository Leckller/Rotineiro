package task

import "errors"

var (
	ErrTaskNotFound      = errors.New("Task not found")
	ErrTaskAlreadyExists = errors.New("Task already exists")
)
