package com.imaginato.homeworkmvvm

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.extra.Utils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Test
    fun validateEmptyUserNameAndPwd() {
        var loginRequest = LoginRequest("", "")
        Assert.assertEquals(
            false,
            Utils.isUserNameValid(loginRequest.username!!) && Utils.isPasswordValid(loginRequest.password!!)
        )
    }

    @Test
    fun validateEmptyUserNameAndFillPwd() {
        var loginRequest = LoginRequest("", "123456")
        Assert.assertEquals(
            false,
            Utils.isUserNameValid(loginRequest.username!!) && Utils.isPasswordValid(loginRequest.password!!)
        )
    }

    @Test
    fun validateFillUserNameAndEmptyPwd() {
        var loginRequest = LoginRequest("username", "")
        Assert.assertEquals(
            false,
            Utils.isUserNameValid(loginRequest.username!!) && Utils.isPasswordValid(loginRequest.password!!)
        )
    }

    @Test
    fun validateUserNameAndPwd() {
        var loginRequest = LoginRequest("usearname", "123456")
        Assert.assertEquals(
            true,
            Utils.isUserNameValid(loginRequest.username!!) && Utils.isPasswordValid(loginRequest.password!!)
        )
    }


    @Test
    fun validateUserNameBigLengthAndPwd() {
        var loginRequest = LoginRequest(
            "RunAndroidinstrumentedtestsusingGradleoptionwasignoredbecausethismoduletypeisnotsupportedyet",
            "123456"
        )
        Assert.assertEquals(
            false,
            Utils.isUserNameValid(loginRequest.username!!) && Utils.isPasswordValid(loginRequest.password!!)
        )
    }
}

