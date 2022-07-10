package com.twoonethree.turtlemint.koin

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.twoonethree.turtlemint.repository.CommentRepository
import com.twoonethree.turtlemint.repository.MainRepository
import com.twoonethree.turtlemint.room.AppDatabase
import com.twoonethree.turtlemint.room.RoomDao
import com.twoonethree.turtlemint.viewmodel.CommentViewModel
import com.twoonethree.turtlemint.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

fun getRoomDataBase(context: Context): AppDatabase {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "localdatabase"
    ).allowMainThreadQueries().build()
    return db
}

fun provideDao(dataBase: AppDatabase): RoomDao {
    return dataBase.roomDao()
}

val appModule = module {

    single { getRoomDataBase(androidContext()) }
    single { provideDao(get()) }
    single { MainRepository(get()) }

    viewModel {
        MainViewModel(get())
    }

    single { CommentRepository() }

    viewModel {
        CommentViewModel(get())
    }
}