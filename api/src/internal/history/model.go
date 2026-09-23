package history

import "gorm.io/gorm"

type History struct {
	gorm.Model
	UserID uint
}
