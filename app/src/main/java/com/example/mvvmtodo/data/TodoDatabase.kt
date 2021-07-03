package com.example.mvvmtodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch

@Database(entities = [Todo::class],version =1)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDAO():TodoDAO

    companion object{
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        //create database
        @InternalCoroutinesApi
        fun getInstance(context: Context):TodoDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,TodoDatabase::class.java,"todo_db")
                        .addCallback(object :Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                CoroutineScope(Dispatchers.IO).launch {
                                    getInstance(context).todoDAO().insertTodo(Todo(0,"Hello","12.00",false))
                                }
                            }
                        }).build()
                }
                return instance
            }
        }
    }

}