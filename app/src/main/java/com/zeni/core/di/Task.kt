package com.zeni.core.di

annotation class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val subTasks: List<SubTask> = emptyList()
)
