package routine

import (
	"api/src/internal/database"
	"api/src/internal/task"
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
	FindByID(userID, routineID uint) (*Routine, error)
	AddTaskToRoutine(userID, routineID, taskID uint) error
	DeleteTaskFromRoutine(userID, routineID, taskID uint) error
}

type service struct {
	repository  Repository
	taskService task.Service
}

func NewService(repository Repository, taskService task.Service) Service {
	return &service{
		repository:  repository,
		taskService: taskService,
	}
}

func (s *service) FindByID(userID, routineID uint) (*Routine, error) {

	routine, err := s.repository.FindByID(routineID)

	if err != nil {
		return nil, err
	}

	if routine == nil {
		return nil, ErrRoutineNotFound
	}

	if routine.UserID != userID {
		return nil, ErrRoutineNotFound
	}

	return routine, nil

}

func (s *service) AddTaskToRoutine(userID, routineID, taskID uint) error {

	resultTask, err := s.taskService.FindByID(userID, taskID)

	if err != nil {
		return err
	}

	resultRoutine, err := s.FindByID(userID, routineID)

	if err != nil {
		return err
	}

	return s.repository.AddTaskToRoutine(*resultRoutine, *resultTask)

}

func (s *service) DeleteTaskFromRoutine(userID, routineID, taskID uint) error {

	resultTask, err := s.taskService.FindByID(userID, taskID)

	if err != nil {
		return err
	}

	resultRoutine, err := s.FindByID(userID, routineID)

	if err != nil {
		return err
	}

	return s.repository.DeleteTaskFromRoutine(*resultRoutine, *resultTask)

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

	if len(updateDTO.Title) < 3 && len(updateDTO.Description) <= 0 {
		return ErrRoutineBadRequest
	}

	routine := &Routine{
		Title:       updateDTO.Title,
		Description: updateDTO.Description,
	}
	routine.ID = updateDTO.ID

	rowsAffected, err := s.repository.Update(userID, routine)

	if err != nil {
		if database.IsUniqueViolation(err) {
			return ErrRoutineAlreadyExists
		}
		return err
	}

	if rowsAffected <= 0 {
		return ErrRoutineNotFound
	}
	print(rowsAffected)

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

	curRoutine, err := s.repository.FindByIDWithEvents(&routineID)

	if err != nil {
		return err
	}

	if curRoutine == nil {
		return ErrRoutineNotFound
	}

	if curRoutine.UserID != userID {
		return ErrRoutineUnauthorized
	}

	if curRoutine.StartedAt == nil {
		return ErrRoutineNotStarted
	}

	// task.TaskEventPause
	// task.TaskEventResume

	for i := 0; i < len(curRoutine.Tasks); i++ {

		curTask := curRoutine.Tasks[i]
		initTime := time.Now()
		var activeTime time.Duration
		var inactiveTime time.Duration

		for j := range curTask.TaskEvents {
			curEvent := curTask.TaskEvents[j]

			if curEvent.EventType == task.TaskEventSkip {

				continue

			} else if curEvent.EventType == task.TaskEventStart {

				if j == len(curTask.TaskEvents)-1 { // evento atual é o último

					if curTask.FinishedAt != nil {
						period := curTask.FinishedAt.Sub(curEvent.CreatedAt)
						activeTime += period
					} else {
						period := initTime.Sub(curEvent.CreatedAt)
						activeTime += period
					}
					continue

				}

				nextEvent := curTask.TaskEvents[(j + 1)]
				period := nextEvent.CreatedAt.Sub(curEvent.CreatedAt)
				activeTime += period

			} else if curEvent.EventType == task.TaskEventPause {

				if j == len(curTask.TaskEvents)-1 { // evento atual é o último

					if curTask.FinishedAt != nil {
						period := curTask.FinishedAt.Sub(curEvent.CreatedAt)
						inactiveTime += period
					} else {
						period := initTime.Sub(curEvent.CreatedAt)
						inactiveTime += period
					}

					continue

				}

				nextEvent := curTask.TaskEvents[(j + 1)]
				period := nextEvent.CreatedAt.Sub(curEvent.CreatedAt)
				inactiveTime += period

			}

		}

	}

	return nil

}
