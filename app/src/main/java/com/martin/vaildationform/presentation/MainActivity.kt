package com.martin.vaildationform.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.martin.vaildationform.R
import com.martin.vaildationform.databinding.ActivityMainBinding
import com.martin.vaildationform.domain.use_cases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private var getValidationState : Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val formUseCases = FormUseCases(ValidateEmail(), ValidatePassword(), ValidateRepeatedPassword(), ValidateTerms())
        val factory = ViewModelFactory(formUseCases) // Factory
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        val view = binding.root
        setContentView(view)

        val state = viewModel.state.value


        binding.submitButton.setOnClickListener {
            viewModel.onEvent(RegistrationFormEvent.Submit)
            getValidationState(context = this)
            getValidationError()
        }

            binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                viewModel.onEvent(RegistrationFormEvent.EmailChanged(binding.etEmail.text.toString()))
              //  Log.i("Tag", viewModel.state.value.email.toString())

            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(binding.etPassword.text.toString()))
               // Log.i("Tag", viewModel.state.value.password.toString())

            }
        })

        binding.etRepeatPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(binding.etRepeatPassword.text.toString()))
              //  Log.i("Tag", viewModel.state.value.repeatedPassword.toString())

            }
        })



    }
  private  fun getValidationState(context: Context) {
      CoroutineScope(Dispatchers.Main).launch {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is MainViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context,
                            "Registration successful",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }
  private  fun getValidationError() {
     lifecycleScope.launchWhenStarted {
         viewModel.state.collectLatest { data ->
             binding.tvEmailError.text = data.emailError
             binding.tvEmailError.setBackgroundColor(data.emailErrorColor)
             binding.tvPasswordError.text = data.passwordError
             binding.tvPasswordError.setBackgroundColor(data.passwordErrorColor)
             binding.tvRepeatPassword.text = data.repeatedPasswordError
             binding.tvRepeatPassword.setBackgroundColor(data.repeatedPasswordErrorColor)
         }
     }
  }

}






