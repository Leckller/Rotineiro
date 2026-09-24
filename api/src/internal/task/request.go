package task

type CreateTaskDTO struct {
	Title       string `json:"title" binding:"required,min=3,max=50"`
	Description string `json:"description" binding:"required,max=200"`
}

type UpdateTaskDTO struct {
	Title       string `json:"title" binding:"min=3,max=50"`
	Description string `json:"description" binding:"max=200"`
}
