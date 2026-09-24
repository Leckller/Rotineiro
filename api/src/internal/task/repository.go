package task

import (
	"api/src/utils"

	"gorm.io/gorm"
)

type Repository interface {
	Create(t *Task) error
	FindByID(id uint) (*Task, error)
	FindAllByUser(userID uint, page, pageSize int) ([]Task, int64, error)
	Update(t *Task) error
	Delete(id uint) error
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

func (r *repository) Update(t *Task) error {
	return r.db.Save(t).Error
}

func (r *repository) Delete(id uint) error {
	return r.db.Delete(&Task{}, id).Error
}
