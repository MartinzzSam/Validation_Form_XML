package com.martin.vaildationform.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martin.vaildationform.domain.use_cases.FormUseCases
import com.martin.vaildationform.domain.use_cases.ValidateEmail

class ViewModelFactory(private val formUseCases: FormUseCases) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(formUseCases) as T
    }
}
