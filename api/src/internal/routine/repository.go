package routine

import (
	"api/src/internal/task"
	"api/src/utils"

	"gorm.io/gorm"
)

type Repository interface {
	Create(routine *Routine) error
	FindAllByUser(userID uint, page, pageSize int) ([]Routine, int64, error)
	Update(userID uint, routine *Routine) (int64, error)
	Delete(userID uint, routine *Routine) (int64, error)
	FindByID(routineID uint) (*Routine, error)
	FindByIDWithEvents(routineID uint) (*Routine, error)
	AddTaskToRoutine(routine Routine, task task.Task) error
	DeleteTaskFromRoutine(routine Routine, task task.Task) error
	CreateHistory(routine *Routine, history *RoutineHistory) error
}

type repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) Repository {
	return &repository{
		db: db,
	}
}

func (r *repository) Create(routine *Routine) error {
	return r.db.Create(routine).Error
}

func (r *repository) FindByID(routineID uint) (*Routine, error) {

	var routine Routine

	if err := r.db.Model(&Routine{}).Preload("Tasks").First(&routine, routineID).Error; err != nil {
		return nil, err
	}

	return &routine, nil

}

func (r *repository) FindByIDWithEvents(routineID uint) (*Routine, error) {
	var routine Routine

	if err := r.db.
		Preload("Tasks").
		Preload("Tasks.TaskEvents", func(db *gorm.DB) *gorm.DB {
			return db.Order("created_at ASC")
		}).
		First(&routine, routineID).
		Error; err != nil {
		return nil, err
	}

	return &routine, nil
}

func (r *repository) CreateHistory(routine *Routine, history *RoutineHistory) error {
	return r.db.Transaction(func(tx *gorm.DB) error {

		if err := tx.Create(history).Error; err != nil {
			return err
		}

		for _, t := range routine.Tasks {

			if err := tx.Where(
				"task_id = ?", t.ID,
			).Delete(&task.TaskEvent{}).Error; err != nil {
				return err
			}

			if err := tx.Model(&task.Task{}).
				Where("id = ?", t.ID).
				Updates(map[string]interface{}{
					"started_at":  nil,
					"finished_at": nil,
				}).Error; err != nil {
				return err
			}

		}

		if err := tx.Model(&Routine{}).
			Where("id = ?", routine.ID).
			Update("started_at", nil).Error; err != nil {
			return err
		}

		return nil

	})
}

func (r *repository) AddTaskToRoutine(routine Routine, task task.Task) error {
	return r.db.
		Model(&routine).
		Association("Tasks").
		Append(&task)
}

func (r *repository) DeleteTaskFromRoutine(routine Routine, task task.Task) error {
	return r.db.
		Model(&routine).
		Association("Tasks").
		Delete(&task)
}

func (r *repository) FindAllByUser(userID uint, page, pageSize int) ([]Routine, int64, error) {

	var routines []Routine
	var total int64

	paginateScope := utils.Paginate(r.db, page, pageSize)

	if err := r.db.
		Model(&Routine{}).
		Where("user_id = ?", userID).
		Count(&total).Error; err != nil {
		return nil, 0, err
	}

	if err := r.db.
		Scopes(paginateScope).
		Where("user_id = ?", userID).
		Find(&routines).Error; err != nil {
		return nil, 0, err
	}

	return routines, total, nil
}

func (r *repository) Update(userID uint, routine *Routine) (int64, error) {

	tx := r.db.
		Model(&Routine{}).
		Where("id = ? AND user_id = ?", routine.ID, userID).
		Updates(&routine)

	if err := tx.Error; err != nil {
		return 0, err
	}

	return tx.RowsAffected, nil

}

func (r *repository) Delete(userID uint, routine *Routine) (int64, error) {

	tx := r.db.
		Model(&Routine{}).
		Unscoped().
		Where("user_id = ?", userID).
		Delete(routine)

	if err := tx.Error; err != nil {
		return 0, err
	}

	return tx.RowsAffected, nil

}
