package routine

import (
	"api/src/internal/task"
	"time"

	"gorm.io/gorm"
)

type Routine struct {
	gorm.Model
	Title       string `gorm:"uniqueIndex:idx_routine_user_title"`
	Description string
	StartedAt   *time.Time
	Tasks       []task.Task `gorm:"many2many:routine_tasks;"`
	UserID      uint        `gorm:"uniqueIndex:idx_routine_user_title"`
}

type RoutineHistory struct {
	gorm.Model
	Title             string
	Description       string
	StartedAt         *time.Time
	TotalTimeActive   *time.Duration
	TotalTimeInactive *time.Duration
	Tasks             []task.TaskHistory `gorm:"foreignKey:RoutineHistoryID"`
	UserID            uint
}
