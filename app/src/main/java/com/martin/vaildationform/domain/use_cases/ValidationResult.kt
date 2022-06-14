package com.martin.vaildationform.domain.use_cases

import android.graphics.Color

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
    val errorColor : Int = Color.WHITE
)
