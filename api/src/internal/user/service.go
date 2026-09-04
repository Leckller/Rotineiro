package user

type Service interface {
	Create(user *User) error
}

type service struct {
	repository Repository
}

func NewService(repository Repository) Service {
	return service{
		repository: repository,
	}
}

func (s service) Create(user *User) error {
	return s.repository.Create(user)
}
