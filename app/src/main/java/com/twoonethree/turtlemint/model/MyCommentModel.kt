package com.twoonethree.turtlemint.model

import androidx.room.PrimaryKey

data class MyCommentModel(
    val like: Int,
    val dislike: Int,
    val comment: String?,
    val username: String?,
    val avatar: String?,
    val updatedtime: String?,
)
