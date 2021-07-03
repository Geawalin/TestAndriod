package com.example.mvvmtodo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO{
    @Insert
    suspend fun insertTodo(todo: Todo )

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getTodos() : LiveData<List<Todo>>

}