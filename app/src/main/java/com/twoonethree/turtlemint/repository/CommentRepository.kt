package com.twoonethree.turtlemint.repository

import android.util.Log
import com.twoonethree.turtlemint.activity.CommentActivity
import com.twoonethree.turtlemint.model.CommentModelItem
import com.twoonethree.turtlemint.model.MyCommentModel
import com.twoonethree.weatherdemo.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentRepository {

    var commentlistflow = MutableStateFlow(listOf<MyCommentModel>())

     fun getAllComment(urlend: Int?)
     {
         val mycommentmodellist = mutableListOf<MyCommentModel>()
         RetrofitClient.getRetrofitClient().getComment(urlend.toString()).enqueue(object: Callback<List<CommentModelItem>>{
             override fun onResponse(
                 call: Call<List<CommentModelItem>>,
                 response: Response<List<CommentModelItem>>
             ) {
                 if (response.isSuccessful && response.body() != null) {
                     val commentmodellist = response.body()
                     if (commentmodellist != null) {
                         for (commentmodel in commentmodellist) {
                             val username = commentmodel.user.login
                             val avatar = commentmodel.user.avatar_url
                             val updatedtime = commentmodel.updated_at.reset()
                             val comment = commentmodel.body
                             val like = commentmodel.reactions.like
                             val dislike = commentmodel.reactions.dislike
                             val mycommentmodel = MyCommentModel(
                                 like,
                                 dislike,
                                 comment,
                                 username,
                                 avatar,
                                 updatedtime
                             )
                             mycommentmodellist.add(mycommentmodel)
                         }
                     }
                     commentlistflow.value = mycommentmodellist
                 }
             }
             override fun onFailure(call: Call<List<CommentModelItem>>, t: Throwable) {
             }
         })
     }
}