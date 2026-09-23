package task

import "api/src/internal/database"

type Service interface {
	Create(createDTO CreateTaskDTO) error
	Update() error
	StartTask() error
	CompleteTask() error
}

type service struct {
	repository Repository
}

func NewService(repository Repository) Service {
	return &service{
		repository: repository,
	}
}

func (s *service) Create(createDTO CreateTaskDTO) error {

	err := s.repository.Create(&Task{
		Title:       createDTO.Title,
		Description: createDTO.Title,
	})

	if err != nil {
		if database.IsUniqueViolation(err) {
			return ErrTaskAlreadyExists
		}
		return err
	}

	return nil

}

func (s *service) Update() error {
	return nil
}

func (s *service) StartTask() error {
	return nil
}

func (s *service) CompleteTask() error {
	return nil
}
