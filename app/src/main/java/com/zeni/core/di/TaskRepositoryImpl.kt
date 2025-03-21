package com.zeni.core.di

import com.zeni.core.di.Task
import com.zeni.core.di.SubTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor() : TaskRepository {

    // Listas mutables para almacenar datos en memoria
    private val tasks = mutableListOf<Task>()
    private val subTasks = mutableListOf<SubTask>()

    override fun getTasks(): List<Task> {
        // Asignamos a cada task las subtareas correspondientes
        return tasks.map { task ->
            task.copy(subTasks = subTasks.filter { it.parentTaskId == task.id })
        }
    }

    override fun addTask(task: Task) {
        // Generamos un id simple basado en el tamaño actual
        val newTask = task.copy(id = tasks.size + 1)
        tasks.add(newTask)
    }

    override fun deleteTask(taskId: Int) {
        tasks.removeAll { it.id == taskId }
        // También eliminamos sus subtareas
        subTasks.removeAll { it.parentTaskId == taskId }
    }

    override fun updateTask(task: Task) {
        // Buscar la tarea por su id y actualizarla
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
    }

    override fun getSubTasksForTask(taskId: Int): List<SubTask> {
        return subTasks.filter { it.parentTaskId == taskId }
    }

    override fun addSubTask(subTask: SubTask) {
        // Generamos un id simple para la subtask
        val newSubTask = subTask.copy(id = subTasks.size + 1)
        subTasks.add(newSubTask)
    }

    override fun deleteSubTask(subTaskId: Int) {
        subTasks.removeAll { it.id == subTaskId }
    }

    override fun updateSubTask(subTask: SubTask) {
        // Buscamos la posición de la subtarea a actualizar
        val index = subTasks.indexOfFirst { it.id == subTask.id }
        if (index != -1) {
            subTasks[index] = subTask
        }
    }
}