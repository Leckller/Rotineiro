package task

import (
	"api/src/utils"

	"gorm.io/gorm"
)

type Repository interface {
	Create(t *Task) error
	FindByID(id uint) (*Task, error)
	FindAllByUser(userID uint, page, pageSize int) ([]Task, int64, error)
	Update(userID uint, task *Task) (int64, error)
	Delete(userID uint, task *Task) (int64, error)
}

type repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) Repository {
	return &repository{
		db: db,
	}
}

func (r *repository) Create(t *Task) error {
	return r.db.Create(t).Error
}

func (r *repository) FindByID(id uint) (*Task, error) {
	var task Task
	if err := r.db.First(&task, id).Error; err != nil {
		return nil, err
	}
	return &task, nil
}

func (r *repository) FindAllByUser(userID uint, page, pageSize int) ([]Task, int64, error) {

	var tasks []Task
	var total int64

	paginateScope := utils.Paginate(r.db, page, pageSize)

	if err := r.db.
		Model(&Task{}).
		Where("user_id = ?", userID).
		Count(&total).Error; err != nil {
		return nil, 0, err
	}

	if err := r.db.
		Scopes(paginateScope).
		Where("user_id = ?", userID).
		Find(&tasks).
		Error; err != nil {
		return nil, 0, err
	}

	return tasks, total, nil

}

func (r *repository) Update(userID uint, task *Task) (int64, error) {

	tx := r.db.
		Model(&Task{}).
		Where("id = ? AND user_id = ?", task.ID, userID).
		Updates(task)

	err := tx.Error

	if err != nil {
		return 0, err
	}

	return tx.RowsAffected, nil

}

func (r *repository) Delete(userID uint, task *Task) (int64, error) {

	tx := r.db.
		Model(&Task{}).
		Where("user_id = ?", userID).
		Unscoped().
		Delete(&task, task.ID)

	if err := tx.Error; err != nil {
		return 0, err
	}

	return tx.RowsAffected, nil

}
