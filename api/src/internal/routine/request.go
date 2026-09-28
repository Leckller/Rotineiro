package routine

type CreateRoutineDTO struct {
	Title       string `json:"title" binding:"required,min=3,max=50"`
	Description string `json:"description" binding:"required,max=200"`
}

type UpdateRoutineDTO struct {
	ID          uint   `json:"id" binding:"required"`
	Title       string `json:"title" binding:"max=50"`
	Description string `json:"description" binding:"max=200"`
}
