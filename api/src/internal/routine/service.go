package routine

import (
	"api/src/internal/database"
	"api/src/utils"
	"math"
	"time"
)

type Service interface {
	StartRotuine(userID, routineID uint) error
	CompleteRotuine(userID, routineID uint) error
	Create(userID uint, createDTO CreateRoutineDTO) (uint, error)
	FindAllByUser(userID uint, page, pageSize int) ([]Routine, utils.PaginationMeta, error)
	Update(userID uint, updateDTO UpdateRoutineDTO) error
	Delete(userID, routineID uint) error
}

type service struct {
	repository Repository
}

func NewService(repository Repository) Service {
	return &service{
		repository: repository,
	}
}

func (s *service) FindAllByUser(userID uint, page, pageSize int) ([]Routine, utils.PaginationMeta, error) {

	routines, total, err := s.repository.FindAllByUser(userID, page, pageSize)

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

	return routines, meta, nil

}

func (s *service) Create(userID uint, createDTO CreateRoutineDTO) (uint, error) {

	var routine Routine = Routine{
		UserID:      userID,
		Title:       createDTO.Title,
		Description: createDTO.Title,
	}

	err := s.repository.Create(&routine)

	if err != nil {
		if database.IsUniqueViolation(err) {
			return 0, ErrRoutineAlreadyExists
		}
		return 0, err
	}

	return routine.ID, nil

}

func (s *service) Update(userID uint, updateDTO UpdateRoutineDTO) error {

	routine := &Routine{
		UserID:      userID,
		Title:       updateDTO.Title,
		Description: updateDTO.Description,
	}

	rowsAffected, err := s.repository.Update(userID, routine)

	if err != nil {
		return err
	}

	if rowsAffected <= 0 {
		return ErrRoutineNotFound
	}

	return nil

}

func (s *service) Delete(userID, routineID uint) error {

	var routine Routine = Routine{}
	routine.ID = routineID

	rowsAffected, err := s.repository.Delete(userID, &routine)

	if err != nil {
		return err
	}

	if rowsAffected <= 0 {
		return ErrRoutineNotFound
	}

	return nil
}

func (s *service) StartRotuine(userID, routineID uint) error {

	now := time.Now()
	var routine Routine = Routine{
		StartedAt: &now,
	}
	routine.ID = routineID

	rowsAffected, err := s.repository.Update(userID, &routine)

	if err != nil {
		return err
	}

	if rowsAffected <= 0 {
		return ErrRoutineNotFound
	}

	return nil
}

func (s *service) CompleteRotuine(userID, routineID uint) error {

	now := time.Now()
	var routine Routine = Routine{
		CompletedAt: &now,
	}
	routine.ID = routineID

	rowsAffected, err := s.repository.Update(userID, &routine)

	if err != nil {
		return err
	}

	if rowsAffected <= 0 {
		return ErrRoutineNotFound
	}

	return nil
}
