package com.martin.vaildationform.domain.use_cases

import android.graphics.Color

class ValidateTerms {

    operator fun invoke(acceptedTerms: Boolean): ValidationResult {
        if(!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms",
                errorColor = Color.RED
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}