package com.martin.vaildationform.presentation

import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.vaildationform.domain.use_cases.FormUseCases
import com.martin.vaildationform.domain.use_cases.ValidateEmail
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel(
 private val useCases: FormUseCases
): ViewModel() {

    private val _state = MutableStateFlow(RegistrationFormState())
    val state : StateFlow<RegistrationFormState> = _state.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()




   fun onEvent(event : RegistrationFormEvent) {
    when (event) {
      is RegistrationFormEvent.EmailChanged -> {
          _state.value = state.value.copy(
              email = event.email
          )

      }
      is RegistrationFormEvent.PasswordChanged -> {
          _state.value = state.value.copy(
              password = event.password
          )

      }
        is RegistrationFormEvent.RepeatedPasswordChanged -> {
            _state.value = state.value.copy(
                repeatedPassword = event.repeatedPassword
            )
        }
        is RegistrationFormEvent.Submit -> {

            submitData()


        }


    }

  }
    private fun submitData() {
        val emailResult = useCases.ValidateEmail(state.value.email)
        val passwordResult = useCases.ValidatePassword(state.value.password)
        val repeatedPasswordResult = useCases.ValidateRepeatedPassword(state.value.password , state.value.repeatedPassword)
        val termsResult = useCases.ValidateTerms(state.value.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
        ).any { !it.successful }

        if(hasError) {
        viewModelScope.launch {
            _state.emit(
                RegistrationFormState(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage,
                    email = state.value.email,
                    password = state.value.password,
                    repeatedPassword = state.value.repeatedPassword,
                    emailErrorColor = emailResult.errorColor,
                    passwordErrorColor = passwordResult.errorColor,
                    repeatedPasswordErrorColor = repeatedPasswordResult.errorColor
                ))
        }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
            _state.emit(
                RegistrationFormState(
                    email = state.value.email,
                    password = state.value.password,
                    repeatedPassword = state.value.repeatedPassword,
                    emailErrorColor = Color.WHITE,
                    passwordErrorColor = Color.WHITE,
                    repeatedPasswordErrorColor = Color.WHITE
                )
            )
        }
    }


    sealed class ValidationEvent {
        object Success: ValidationEvent()
        object Failed : ValidationEvent()
    }

}