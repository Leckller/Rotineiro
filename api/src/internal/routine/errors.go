package routine

import "errors"

var (
	ErrRoutineNotFound      = errors.New("Routine not found")
	ErrRoutineNotStarted    = errors.New("Routine not started")
	ErrRoutineBadRequest    = errors.New("Invalid routine request")
	ErrRoutineUnauthorized  = errors.New("Unauthorized request")
	ErrRoutineAlreadyExists = errors.New("Routine already exists")
)
