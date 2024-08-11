package com.app.task.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.task.databinding.ItemAdapterBinding
import com.app.task.model.Task

class TaskAdapter(
    private val taskList: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder
    }

    inner class TaskViewHolder(val binding: ItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)
}