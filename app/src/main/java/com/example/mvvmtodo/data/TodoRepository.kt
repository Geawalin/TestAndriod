package com.example.mvvmtodo.data

class TodoRepository (private val dao: TodoDAO){
    val todos = dao.getTodos()

    suspend fun insert(todo: Todo){
        return dao.insertTodo(todo)
    }

    suspend fun delete(todo: Todo){
        return dao.deleteTodo(todo)
    }

    suspend fun update(todo: Todo){
        return dao.updateTodo(todo)
    }
}