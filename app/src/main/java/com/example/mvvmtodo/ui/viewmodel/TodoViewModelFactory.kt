package com.example.mvvmtodo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtodo.data.TodoRepository
import java.lang.IllegalArgumentException

class TodoViewModelFactory(val repository: TodoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewmodel::class.java)){
            return TodoViewmodel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}