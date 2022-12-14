package com.imaginato.homeworkmvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDatabase
import com.imaginato.homeworkmvvm.data.remote.login.LoginDataRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import com.imaginato.homeworkmvvm.extra.Utils
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import com.imaginato.homeworkmvvm.ui.login.LoginFormState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject

@KoinApiExtension
class LoginActivityViewModel : BaseViewModel() {
    val repository: LoginRepository by inject()

    private val loginDatabase: LoginDatabase by inject()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private var _resultLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean>
        get() {
            return _progress
        }

    val resultLiveData: LiveData<LoginResponse>
        get() {
            return _resultLiveData
        }
    val loginRepo: LoginRepository
        get() {
            return repository
        }

    fun getLoginData(loginRequest: LoginRequest) {
        viewModelScope.launch {
            repository.getLoginData(loginRequest)
                .onStart {
                    _progress.value = true
                }.catch {
                    _progress.value = false
                }
                .onCompletion {
                }.collect {
                    _progress.value = false
                    if (it.errorCode.equals("00")) {
                        _resultLiveData.value = it
                        loginDatabase.loginDao.insertUserData(
                            Login(
                                it.data!!.userId!!,
                                it.data!!.userName!!,
                                System.currentTimeMillis(),
                                key = LoginDataRepository.key
                            )
                        )
                    }

                }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!Utils.isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!Utils.isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

}