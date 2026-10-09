package com.example.amiiboapp.domain.usecase

import com.example.amiiboapp.domain.model.Note

class ValidateNoteUseCase {
    operator fun invoke(note: Note): ValidationResult{
        val errors = NoteCreateErrors()
        var isValid = true

        if (note.ratingNote > 5 || note.ratingNote < 1){
            isValid = false
            errors.starsCount = "укажите рейтинг от 1 до 5"
        }

        if (note.text == "" ){
            isValid = false
            errors.starsCount = "Текст не должен быть пустым"
        }

        return ValidationResult(false, errors)
    }


}



data class ValidationResult(
    val isValid: Boolean,
    val errors:  NoteCreateErrors
)



data class NoteCreateErrors(
    var starsCount: String? = null,
    var emptyText: String? = null,

)