package routine

import (
	"api/src/utils"

	"gorm.io/gorm"
)

type Repository interface {
	Create(userID uint, routine *Routine) error
	FindAllByUser(userID uint, page, pageSize int) ([]Routine, int64, error)
	Update(userID uint, routine *Routine) (int64, error)
	Delete(userID uint, routine *Routine) (int64, error)
}

type repository struct {
	db *gorm.DB
}

func NewRepository(db *gorm.DB) Repository {
	return &repository{
		db: db,
	}
}

func (r *repository) Create(userID uint, routine *Routine) error {
	return r.db.Create(routine).Error
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
		Where("user_id = ?", userID).
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
