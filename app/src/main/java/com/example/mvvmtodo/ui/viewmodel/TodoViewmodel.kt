package com.example.mvvmtodo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.Todo
import com.example.mvvmtodo.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class TodoViewmodel (private val repository: TodoRepository): ViewModel(){
    val todoList : LiveData<List<Todo>> = repository.todos

    fun insert(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todo)

        }
    }

    fun delete(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(todo)

        }
    }
    fun update(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(todo)

        }
    }

}