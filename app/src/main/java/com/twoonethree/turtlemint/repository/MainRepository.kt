package com.twoonethree.turtlemint.repository

import android.util.Log
import com.twoonethree.turtlemint.model.IssueModelItem
import com.twoonethree.turtlemint.model.MyCommentModel
import com.twoonethree.turtlemint.room.RoomDao
import com.twoonethree.turtlemint.room.RoomIssueModel
import com.twoonethree.turtlemint.sealed.ApiData
import com.twoonethree.weatherdemo.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainRepository(val roomDao: RoomDao) {

    var commentlistflow = MutableStateFlow<ApiData>(ApiData.empty)

    fun getAllIssue(): List<RoomIssueModel> {
         return roomDao.getAll()
    }

    fun insertAllIssue(){
        fetchAllIssue()
    }

    private fun fetchAllIssue() {
        commentlistflow.value = ApiData.loading
        val issuelist = mutableListOf<RoomIssueModel>()

        RetrofitClient.getRetrofitClient().getIssue().enqueue(object:
            Callback<List<IssueModelItem>> {
            override fun onResponse(call: Call<List<IssueModelItem>>, response: Response<List<IssueModelItem>>) {
                if(response.isSuccessful && response.body() != null)
                {
                    val issuemodelitemlist = response.body()
                    if (issuemodelitemlist != null) {
                        for(issuemodelitem in issuemodelitemlist)
                        {
                            val userid = issuemodelitem.id
                            val title = issuemodelitem.title
                            val description = issuemodelitem.body
                            val username = issuemodelitem.user.login
                            val avatar = issuemodelitem.user.avatar_url
                            val updated = issuemodelitem.updated_at.reset()
                            val comment = issuemodelitem.comments
                            val commentid = issuemodelitem.number
                            val comment_url = issuemodelitem.comments_url
                            val roomIssueModel = RoomIssueModel(
                                    userid,
                                    title,
                                    description,
                                    username,
                                    avatar,
                                    updated,
                                    comment,
                                    commentid,
                                    comment_url)
                            issuelist.add(roomIssueModel)
                        }
                        for(issue in issuelist)
                        {
                            roomDao.insertIssue(issue)
                        }
                        commentlistflow.value = ApiData.success
                    }
                }
            }
            override fun onFailure(call: Call<List<IssueModelItem>>, t: Throwable) {
                commentlistflow.value = ApiData.failed
                Log.e("TAG", "onFailure: ${t.message}", )
            }
        })

    }
}

fun String.reset(): String {
    val stringaaray =  this.substring(0, 10).split("-")
    return "${stringaaray.get(1)}/${stringaaray.get(2)}/${stringaaray.get(0)}"
}