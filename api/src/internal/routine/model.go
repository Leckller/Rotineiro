package routine

import "gorm.io/gorm"

type Routine struct {
	gorm.Model
	Name        string
	Description string
	UserID      uint
}
