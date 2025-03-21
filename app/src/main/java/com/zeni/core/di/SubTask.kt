package com.zeni.core.di

annotation class SubTask(
    val id: Int = 0,
    val parentTaskId: Int,
    val title: String,
    val description: String,
)
