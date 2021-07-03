package com.example.mvvmtodo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtodo.R
import com.example.mvvmtodo.data.Todo
import com.example.mvvmtodo.data.TodoDatabase
import com.example.mvvmtodo.data.TodoRepository
import com.example.mvvmtodo.databinding.ActivityMainBinding
import com.example.mvvmtodo.ui.viewmodel.TodoViewModelFactory
import com.example.mvvmtodo.ui.viewmodel.TodoViewmodel
import com.example.mvvmtodo.utils.TodoRecyclerAdapter
import com.example.mvvmtodo.utils.onCheckListener
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity(),onCheckListener {
    lateinit var binding: ActivityMainBinding
    lateinit var todoRecyclerAdapter: TodoRecyclerAdapter
    lateinit var todoViewmodel: TodoViewmodel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initUI()
    }

    @InternalCoroutinesApi
    private fun initUI(){
        val dao = TodoDatabase.getInstance(this).todoDAO()
        val repository = TodoRepository(dao)
        val factory = TodoViewModelFactory(repository)
        todoViewmodel = ViewModelProvider(this,factory).get(TodoViewmodel::class.java)
        todoRecyclerAdapter = TodoRecyclerAdapter(this)
        binding.todoRecyclerview.layoutManager = LinearLayoutManager(this)


        observeData()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this,AddTodoActivity::class.java)
            startActivityForResult(intent,REQUEST_ADD)
        }
        swipeHelper()
    }
    private fun swipeHelper(){
        val itemTouchHelperSimpleCallback = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoViewmodel.delete(todoViewmodel.todoList.value!!.get(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperSimpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoRecyclerview)
    }

    private fun observeData(){
        todoViewmodel.todoList.observe(this, Observer{
            todoRecyclerAdapter.submitList(it)
            binding.todoRecyclerview.adapter = todoRecyclerAdapter
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ADD && resultCode == REQUEST_ADD){
            val bundle = data!!.getBundleExtra("key")
            val todo =bundle!!.getParcelable<Todo>("bundle")
            todoViewmodel.insert(todo!!)
            Log.i("item",todo.toString())
        }
    }

    companion object{
        val REQUEST_ADD = 1
    }

    override fun onCheckBox(position: Int, state: Boolean) {
        Toast.makeText(this,state.toString(),Toast.LENGTH_SHORT).show()
        val item = todoViewmodel.todoList.value!!.get(position)
        todoViewmodel.update(Todo(item.id,item.title,item.time,state))
    }
}