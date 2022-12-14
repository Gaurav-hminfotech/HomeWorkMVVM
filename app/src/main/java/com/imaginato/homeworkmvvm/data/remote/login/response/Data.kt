package com.imaginato.homeworkmvvm.data.remote.login.response

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("userId"    ) var userId    : String?  = null,
  @SerializedName("userName"  ) var userName  : String?  = null,
  @SerializedName("isDeleted" ) var isDeleted : Boolean? = null

)