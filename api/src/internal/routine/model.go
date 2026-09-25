package routine

import (
	"api/src/internal/task"
	"time"

	"gorm.io/gorm"
)

type Routine struct {
	gorm.Model
	Name        string
	Description string
	StartedAt   *time.Time
	CompletedAt *time.Time
	tasks       []task.Task `gorm:"many2many:routine_tasks;"`
	UserID      uint
}
