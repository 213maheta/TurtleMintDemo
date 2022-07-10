package com.twoonethree.turtlemint.viewmodel

import androidx.lifecycle.ViewModel
import com.twoonethree.turtlemint.repository.CommentRepository

class CommentViewModel(val repositoty: CommentRepository): ViewModel() {


    fun getAllComment(urlend: Int?)
    {
        repositoty.getAllComment(urlend)
    }

}