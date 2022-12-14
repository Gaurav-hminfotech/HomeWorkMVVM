package com.imaginato.homeworkmvvm.ui.login

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.Data
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.exts.afterTextChanged
import com.imaginato.homeworkmvvm.exts.toast
import com.imaginato.homeworkmvvm.ui.LoginActivityViewModel
import org.koin.core.component.KoinApiExtension

import org.koin.androidx.viewmodel.ext.android.viewModel


@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModel<LoginActivityViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        initTextChanges()
    }

    private fun initTextChanges() {
        binding.edtUsername?.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.edtUsername!!.text.toString(),
                binding.edtPassword!!.text.toString()
            )
        }

        binding.edtPassword?.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.edtUsername!!.text.toString(),
                    binding.edtPassword!!.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel!!.getLoginData(
                            LoginRequest(
                                binding.edtUsername!!.text.toString(),
                                binding.edtPassword!!.text.toString()
                            )
                        )
                }
                false
            }

            binding.login.setOnClickListener {
                binding.pbLoading!!.visibility = View.VISIBLE
                loginViewModel!!.getLoginData(
                    LoginRequest(
                        binding.edtUsername!!.text.toString(),
                        binding.edtPassword!!.text.toString()
                    )

                )
            }
        }
    }


    private fun initObserver() {
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            binding.txtPwdError!!.text = ""
            binding.txtUsernameError!!.text = ""

            if (loginState.usernameError != null) {
                binding.txtUsernameError!!.text = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.txtPwdError!!.text = getString(loginState.passwordError)
            }
        })

        loginViewModel.resultLiveData.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            binding.pbLoading!!.visibility = View.GONE
            if (!loginResult.errorCode.equals("00")) {
                loginResult.errorMessage!!.toast(this@LoginActivity)
            } else {
                updateUiWithUser(loginResult.data)
            }

        })
    }

    private fun updateUiWithUser(model: Data?) {
        val welcome = getString(R.string.welcome)
        val displayName = model!!.userName
        "$welcome $displayName".toast(this@LoginActivity)
    }
}
