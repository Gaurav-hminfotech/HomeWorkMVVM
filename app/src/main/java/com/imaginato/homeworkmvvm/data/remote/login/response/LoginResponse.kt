package com.imaginato.homeworkmvvm.data.remote.login.response

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow


data class LoginResponse (

  @SerializedName("errorCode"    ) var errorCode    : String? = null,
  @SerializedName("errorMessage" ) var errorMessage : String? = null,
  @SerializedName("data"         ) var data         : Data?   = Data()

)