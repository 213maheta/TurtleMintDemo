package com.twoonethree.weatherdemo

import com.twoonethree.turtlemint.model.CommentModelItem
import com.twoonethree.turtlemint.model.IssueModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
     @GET("issues")
     fun getIssue() : Call<List<IssueModelItem>>

     @GET("issues/{comment_id}/comments")
     fun getComment(@Path(value = "comment_id", encoded = true) commentId:String) : Call<List<CommentModelItem>>
}