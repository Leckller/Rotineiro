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
	startedAt   time.Time
	finishedAt  time.Time
	tasks       []task.Task `gorm:"many2many:routine_tasks;"`
	UserID      uint
}
