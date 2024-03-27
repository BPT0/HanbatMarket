package com.hanbat.hanbatmarket.signin

import com.google.gson.annotations.SerializedName

data class SigninDTO(
    @SerializedName("mail")
    val mail: String,

    @SerializedName("passwd")
    val passwd: String,

    @SerializedName("nickname")
    val nickname: String,
)

data class LoginDTO(
    @SerializedName("mail")
    val mail: String,

    @SerializedName("passwd")
    val passwd: String,
)

