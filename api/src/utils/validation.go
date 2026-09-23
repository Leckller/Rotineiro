package utils

import (
	"errors"
	"fmt"
	"reflect"
	"strings"

	"github.com/gin-gonic/gin/binding"
	"github.com/go-playground/validator/v10"
)

// RegisterJSONTagNames faz o validator usar os nomes das tags `json`
// ao invés do nome do campo Go nas mensagens de erro.
// Isso aq é meio desnecessário pq geralmente só vai mudar a primeira letra estar maiúscula ou não... mas fé eu gosto
func RegisterJSONTagNames() {
	if v, ok := binding.Validator.Engine().(*validator.Validate); ok {
		v.RegisterTagNameFunc(func(fld reflect.StructField) string {
			name := strings.SplitN(fld.Tag.Get("json"), ",", 2)[0]
			if name == "-" {
				return ""
			}
			return name
		})
	}
}

// FormatValidationErrors converte um erro de binding do Gin/validator
// num map[campo]mensagem amigável. Retorna nil se o erro não for
// de validação
func FormatValidationErrors(err error) map[string]string {
	var ve validator.ValidationErrors
	if !isValidationError(err, &ve) {
		return nil
	}

	out := make(map[string]string, len(ve))
	for _, fe := range ve {
		out[fe.Field()] = customMessage(fe)
	}
	return out
}

func isValidationError(err error, target *validator.ValidationErrors) bool {
	return err != nil && errors.As(err, target)
}

func customMessage(fe validator.FieldError) string {
	switch fe.Tag() {
	case "required":
		return fmt.Sprintf("O campo %s é obrigatório", fe.Field())
	case "email":
		return fmt.Sprintf("O campo %s deve ser um e-mail válido", fe.Field())
	case "min":
		return fmt.Sprintf("O campo %s deve ter no mínimo %s caracteres", fe.Field(), fe.Param())
	case "max":
		return fmt.Sprintf("O campo %s deve ter no máximo %s caracteres", fe.Field(), fe.Param())
	default:
		return fmt.Sprintf("O campo %s é inválido", fe.Field())
	}
}
