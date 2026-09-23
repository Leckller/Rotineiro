package database

import "strings"

func IsUniqueViolation(err error) bool {
	return strings.Contains(err.Error(), "UNIQUE constraint failed")
}

// qndo trocar p postgres ->

// func isUniqueViolation(err error) bool {
//     var pgErr *pgx.Error

//     if errors.As(err, &pgErr) {
//         return pgErr.Code == "23505"
//     }

//     return false
// }
