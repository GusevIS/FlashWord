package com.example.flashword.data.mapper

import com.example.flashword.data.model.UserDto
import com.example.flashword.domain.model.UserModel

fun UserDto.toUserModel(): UserModel =
    UserModel(
        name = nameParam,
        pictureUrl = pictureUrlParam
    )

fun UserModel.toUserDto(): UserDto =
    UserDto(
        nameParam = name,
        pictureUrlParam = pictureUrl
    )