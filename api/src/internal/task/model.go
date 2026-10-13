package task

import (
	"time"

	"gorm.io/gorm"
)

var (
	TaskEventStart     = "start"
	TaskEventPause     = "pause"
	TaskEventResume    = "resume"
	TaskEventCompleted = "completed"
)

type Task struct {
	gorm.Model
	Title       string `gorm:"uniqueIndex:idx_tasks_user_title"`
	Description string
	StartedAt   *time.Time
	CompletedAt *time.Time
	TaskEvents  []TaskEvent
	UserID      uint `gorm:"uniqueIndex:idx_tasks_user_title"`
}

type TaskEvent struct {
	gorm.Model
	EventType string // start, pause, finished
	TaskId    uint
}

type TaskHistory struct {
	gorm.Model
	Title             string
	Description       string
	StartedAt         *time.Time
	CompletedAt       *time.Time
	TotalTimeActive   *time.Time
	TotalTimeInactive *time.Time
}
