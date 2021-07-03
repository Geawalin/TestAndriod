package com.example.mvvmtodo.utils

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtodo.R
import com.example.mvvmtodo.data.Todo
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoRecyclerAdapter(private val onCheckListener: onCheckListener): RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder>(){
    var todoList = listOf<Todo>()
    class TodoViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.title_textView
        val timeTextView = itemView.time_textView
        val checkBox = itemView.isComplete_checkbox

        fun bind(todo: Todo, action:onCheckListener){
            titleTextView.text = todo.title
            timeTextView.text = todo.time
            checkBox.isChecked = todo.isComplete

            if(checkBox.isChecked)
            {
                titleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    action.onCheckBox(adapterPosition,true)
                }else{
                    action.onCheckBox(adapterPosition,false)

                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoViewHolder(itemView)
    }

    fun submitList(list: List<Todo>){
        this.todoList = list
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = todoList[position]
        holder.bind(current,onCheckListener)
    }

    override fun getItemCount(): Int = todoList.size

}
interface onCheckListener{
    fun onCheckBox(position: Int,state:Boolean)
}