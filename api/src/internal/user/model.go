package user

import (
	"api/src/internal/routine"
	"api/src/internal/task"

	"gorm.io/gorm"
)

type User struct {
	gorm.Model
	Name     string
	Email    string
	Password string
	Tasks    []task.Task
	Routines []routine.Routine
}
