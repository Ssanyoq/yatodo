package com.example.yatodo.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.yatodo.R
import com.example.yatodo.data.TodoItem
import com.example.yatodo.domain.TodoItemDiffCalculator
import com.example.yatodo.viewmodel.TodoItemsViewModel


/**
 * Self-explanatory adapter for RecyclerView with diffCalculator
 */
class TodoItemAdapter(
    private val viewModel: TodoItemsViewModel,
    diffCalculator: TodoItemDiffCalculator
) : ListAdapter<TodoItem, TodoItemViewHolder>(diffCalculator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(
            layoutInflater.inflate(
                R.layout.todo_item,
                parent,
                false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }
}
