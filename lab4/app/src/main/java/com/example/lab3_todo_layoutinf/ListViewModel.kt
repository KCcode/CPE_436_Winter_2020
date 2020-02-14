package com.example.lab3_todo_layoutinf

import androidx.lifecycle.ViewModel

class ListViewModel: ViewModel() {
    val list = mutableListOf<Task>()

    fun addTask(task : Task){
        list.add(task)

    }
}