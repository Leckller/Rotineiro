package task

import (
	"time"

	"gorm.io/gorm"
)

type Task struct {
	gorm.Model
	Title       string
	Description string
	startedAt   time.Time
	completedAt time.Time
	UserID      uint
}
