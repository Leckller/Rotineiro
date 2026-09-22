package user

type Service interface {
	Create(user *User) error
	Login(email string, password string) (string, error)
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

func (s service) Login(email string, password string) (string, error) {

	user, err := s.repository.GetUserByEmail(email)

	if err != nil {
		return "", err
	}

	if password != user.Password {
		return "", err
	}

	return "token", nil

}
