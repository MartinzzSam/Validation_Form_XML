package com.martin.vaildationform.domain.use_cases

import android.graphics.Color
import android.util.Patterns

class ValidateEmail {
    operator fun invoke(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank",
                errorColor = Color.RED
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email",
                errorColor = Color.RED
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}