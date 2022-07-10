package com.twoonethree.turtlemint.room

import androidx.room.*

@Dao
interface RoomDao {

    @Query("SELECT * FROM RoomIssueModel")
    fun getAll(): List<RoomIssueModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssue(roomIssueModel: RoomIssueModel)

}