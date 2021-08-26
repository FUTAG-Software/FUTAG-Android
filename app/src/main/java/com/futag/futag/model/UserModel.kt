package com.futag.futag.model

import com.google.firebase.Timestamp

data class UserModel(
    var name: String,
    var surname: String,
    val email: String,
    val userUid: String,
    var birthday: String,
    var profileImage: String?,
    var profileImageName: String?,
    val registrationTime: Timestamp
)