package com.imaginato.homeworkmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.remote.login.LoginApi
import com.imaginato.homeworkmvvm.data.remote.login.LoginDataRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.Data
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import com.imaginato.homeworkmvvm.ui.LoginActivityViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.KoinApiExtension
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.Retrofit


@RunWith(JUnit4::class)
class LoginApiCallTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @OptIn(KoinApiExtension::class)
    @Test
    fun validateLoginApiCall() {
        var loginRequest = LoginRequest("username", "1111111")


        var retroResponse = LoginResponse(
            "00",
            "success",
            Data(userId = "111111", userName = "Username", isDeleted = false)
        )
        val loginActivityViewModel = LoginActivityViewModel()
        val channel = Channel<LoginResponse>()
        val flow = channel.consumeAsFlow()

        runBlocking {
            Mockito.`when`(loginRepository.getLoginData(loginRequest)).thenReturn(flow)

            launch { channel.send(retroResponse) }
            loginActivityViewModel.getLoginData(loginRequest)

            if (loginActivityViewModel.resultLiveData.value?.errorCode.equals("00")) {
                Truth.assertThat(true).isTrue()
            } else {
                Truth.assertThat(false).isFalse()
            }

        }
    }
}
