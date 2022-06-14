package com.martin.vaildationform.presentation

import android.graphics.Color

data class RegistrationFormState(
    val email: String = "",
    val emailError: String? = null,
    val emailErrorColor: Int = Color.WHITE,
    val password: String = "",
    val passwordError: String? = null,
    val passwordErrorColor: Int  = Color.WHITE,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val repeatedPasswordErrorColor: Int = Color.WHITE,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null,

)
