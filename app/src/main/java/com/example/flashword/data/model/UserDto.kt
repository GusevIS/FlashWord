package com.example.flashword.data.model

import com.google.gson.annotations.SerializedName

data class UserDto (
    @SerializedName("name_param")
    val nameParam: String? = "",
    @SerializedName("picture_url_param")
    val pictureUrlParam: String? = "",

)