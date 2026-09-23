package task

import "gorm.io/gorm"

type Repository interface {
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

func (r *repository) FindAllByUser(userID uint) ([]Task, error) {
	var tasks []Task
	if err := r.db.Where("user_id = ?", userID).Find(&tasks).Error; err != nil {
		return nil, err
	}
	return tasks, nil
}

func (r *repository) Update(t *Task) error {
	return r.db.Save(t).Error
}

func (r *repository) Delete(id uint) error {
	return r.db.Delete(&Task{}, id).Error
}
