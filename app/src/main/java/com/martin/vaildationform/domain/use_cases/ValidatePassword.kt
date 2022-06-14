package com.martin.vaildationform.domain.use_cases

import android.graphics.Color

class ValidatePassword {
    operator fun invoke(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters",
                errorColor = Color.RED
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit",
                errorColor = Color.RED
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}