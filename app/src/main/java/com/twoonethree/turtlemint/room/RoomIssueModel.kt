package com.twoonethree.turtlemint.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomIssueModel(
    @PrimaryKey val userid: Int,
    val title: String?,
    val description: String?,
    val username: String?,
    val avatar: String?,
    val updatedtime: String?,
    val comment:Int?,
    val commentid:Int?,
    val comment_url:String?
)
