package com.twoonethree.turtlemint.viewmodel

import androidx.lifecycle.ViewModel
import com.twoonethree.turtlemint.repository.MainRepository
import com.twoonethree.turtlemint.room.RoomDao
import com.twoonethree.turtlemint.room.RoomIssueModel

class MainViewModel(val repositoty: MainRepository) : ViewModel() {

    fun getAllIssue(): List<RoomIssueModel> {
        return repositoty.getAllIssue()
    }

    fun insertAllIssue() {
        repositoty.insertAllIssue()
    }
}