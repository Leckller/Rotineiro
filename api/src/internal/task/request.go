package task

type CreateTaskDTO struct {
	Title       string `json:"title" binding:"required,min=3,max=50"`
	Description string `json:"description" binding:"required,max=200"`
}

type UpdateTaskDTO struct {
	ID          uint   `json:"id" binding:"required"`
	Title       string `json:"title" binding:"max=50"` // a validação do min fica manual p poder dxar opcional
	Description string `json:"description" binding:"max=200"`
}
