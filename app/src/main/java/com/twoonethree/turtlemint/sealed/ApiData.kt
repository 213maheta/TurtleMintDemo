package com.twoonethree.turtlemint.sealed

import com.twoonethree.turtlemint.room.RoomIssueModel

sealed class ApiData{
    object loading:ApiData()
    object success:ApiData()
    object failed:ApiData()
    object empty:ApiData()
}
