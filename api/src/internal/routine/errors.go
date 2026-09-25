package routine

import "errors"

var (
	ErrRoutineNotFound      = errors.New("Routine not found")
	ErrRoutineBadRequest    = errors.New("Invalid routine request")
	ErrRoutineAlreadyExists = errors.New("Routine already exists")
)
