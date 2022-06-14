package com.martin.vaildationform.domain.use_cases

data class FormUseCases(
    val ValidateEmail : ValidateEmail,
    val ValidatePassword : ValidatePassword,
    val ValidateRepeatedPassword : ValidateRepeatedPassword,
    val ValidateTerms : ValidateTerms,


)
