package user

import (
	"gorm.io/gorm"
)

type Repository interface {
	Create(user *User) error
	GetUserByID(userId uint) (*User, error)
	GetUserByEmail(email string) (*User, error)
}

type repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) Repository {
	return &repository{
		db: db,
	}
}

func (r *repository) Create(user *User) error {

	return r.db.Create(user).Error

}

func (r *repository) GetUserByID(userId uint) (*User, error) {

	var user User = User{}

	if err := r.db.Where("id = ?", userId).Find(&user).Error; err != nil {
		return nil, err
	}

	return &user, nil

}

func (r *repository) GetUserByEmail(email string) (*User, error) {

	var user User = User{}

	if err := r.db.Where("email = ?", email).Find(&user).Error; err != nil {
		return nil, err
	}

	return &user, nil

}
