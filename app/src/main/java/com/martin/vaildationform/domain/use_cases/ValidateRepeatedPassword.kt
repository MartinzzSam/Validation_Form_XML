package com.martin.vaildationform.domain.use_cases

import android.graphics.Color

class ValidateRepeatedPassword {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match",
                errorColor = Color.RED
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}