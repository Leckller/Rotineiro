package task

import (
	"api/src/internal/database"
	"api/src/utils"
	"math"
)

type Service interface {
	Create(userID uint, createDTO CreateTaskDTO) (uint, error)
	Update(userID uint, updateDTO UpdateTaskDTO) error
	StartTask(userID, taskID uint) error
	CompleteTask() error
	FindAllByUser(
		userID uint,
		page,
		pageSize int,
	) ([]Task, utils.PaginationMeta, error)
}

type service struct {
	repository Repository
}

func NewService(repository Repository) Service {
	return &service{
		repository: repository,
	}
}

func (s service) FindAllByUser(
	userID uint,
	page,
	pageSize int,
) ([]Task, utils.PaginationMeta, error) {

	tasks, total, err := s.repository.FindAllByUser(
		userID,
		page,
		pageSize,
	)

	if err != nil {
		return nil, utils.PaginationMeta{}, err
	}

	totalPages := int(math.Ceil(float64(total) / float64(pageSize)))

	meta := utils.PaginationMeta{
		Page:       page,
		PageSize:   pageSize,
		Total:      int(total),
		TotalPages: totalPages,
	}

	return tasks, meta, nil
}

func (s *service) Create(userID uint, createDTO CreateTaskDTO) (uint, error) {

	var task Task = Task{
		UserID:      userID,
		Title:       createDTO.Title,
		Description: createDTO.Title,
	}

	err := s.repository.Create(&task)

	if err != nil {
		if database.IsUniqueViolation(err) {
			return 0, ErrTaskAlreadyExists
		}
		return 0, err
	}

	return task.ID, nil

}

func (s *service) Update(userID uint, updateDTO UpdateTaskDTO) error {

	if len(updateDTO.Title) < 3 && len(updateDTO.Description) <= 0 {
		return ErrTaskBadRequest
	}

	var task Task = Task{
		Title:       updateDTO.Title,
		Description: updateDTO.Description,
	}
	task.ID = updateDTO.ID

	rowsAffected, err := s.repository.Update(userID, &task)

	if err != nil {

		if database.IsUniqueViolation(err) {
			return ErrTaskAlreadyExists
		}
		return err

	}

	if rowsAffected <= 0 {
		return ErrTaskNotFound
	}

	return nil

}

func (s *service) StartTask(userID, taskID uint) error {

	// var task Task = Task{
	// 	Title:       updateDTO.Title,
	// 	Description: updateDTO.Title,
	// }

	// err := s.repository.Update(&task)

	return nil
}

func (s *service) CompleteTask() error {
	return nil
}
