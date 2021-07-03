package com.example.mvvmtodo.ui

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.mvvmtodo.R
import com.example.mvvmtodo.data.Todo
import com.example.mvvmtodo.databinding.ActivityAddTodoBinding
import com.example.mvvmtodo.databinding.ActivityMainBinding
import com.example.mvvmtodo.ui.MainActivity.Companion.REQUEST_ADD
import java.util.*

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_todo)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_todo_item -> {
                saveTodo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTodo(){
        val title = binding.titleEditText.text.toString()
        val time = binding.timeEditText.text.toString()

        val todo = Todo(0,title,time,false)
        val bundle = bundleOf("bundle" to todo)
        val intent = Intent()
        intent.putExtra("key",bundle)
        setResult(REQUEST_ADD,intent)
        finish()
    }

    fun openTimePicker(view: View){
        val calendar = Calendar.getInstance()
        val timeListener =TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val msg = checkTime(hourOfDay,minute)
            binding.timeEditText.setText(msg)
        }
        val dialog = TimePickerDialog(this,timeListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true)
        dialog.show()
    }
    private fun checkTime(hourOfDay:Int,minute:Int):String {
        var msg = ""
        if (hourOfDay < 10 && minute < 10) {
            msg = "0$hourOfDay:0$minute"
        }
        else if (hourOfDay > 10 && minute < 10) {
            msg = "$hourOfDay:0$minute"
        }
        else if (hourOfDay < 10 && minute > 10) {
            msg = "0$hourOfDay:$minute"
        }
        else {
            msg = "$hourOfDay:$minute"
        }
        return msg
    }
}