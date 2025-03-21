package com.zeni.core.di

open annotation class TaskRepository {
    open fun getTasks(): List<Task>
    open fun addTask(task: Task)
    open fun deleteTask(taskId: Int)
    open fun updateTask(task: Task)

    open fun getSubTasksForTask(taskId: Int): List<SubTask>
    open fun addSubTask(subTask: SubTask)
    open fun deleteSubTask(subTaskId: Int)
    open fun updateSubTask(subTask: SubTask)
}
