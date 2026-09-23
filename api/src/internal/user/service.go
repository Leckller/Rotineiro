package user

import (
	"api/src/internal/database"
	"api/src/utils"
)

type Service interface {
	Create(user *User) (string, error)
	Login(email string, password string) (string, error)
}

type service struct {
	repository Repository
}

func NewService(repository Repository) Service {
	return service{
		repository: repository,
	}
}

func (s service) Create(user *User) (string, error) {

	err := s.repository.Create(user)

	if err != nil {
		if database.IsUniqueViolation(err) {
			return "", ErrEmailAlreadyExists
		}
		return "", err
	}

	token, err := utils.CreateToken(user.Email)

	if err != nil {
		return "", err
	}

	return token, nil

}

func (s service) Login(email string, password string) (string, error) {

	user, err := s.repository.GetUserByEmail(email)

	if err != nil {
		return "", err
	}

	if password != user.Password {
		return "", ErrInvalidCredentials
	}

	token, err := utils.CreateToken(user.Email)

	if err != nil {
		return "", err
	}

	return token, nil

}
