package task

import (
	"time"

	"gorm.io/gorm"
)

type Task struct {
	gorm.Model
	Title       string `gorm:"uniqueIndex:idx_tasks_user_title"`
	Description string
	StartedAt   *time.Time
	CompletedAt *time.Time
	UserID      uint `gorm:"uniqueIndex:idx_tasks_user_title"`
}
