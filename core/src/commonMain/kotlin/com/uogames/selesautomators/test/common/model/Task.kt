package com.uogames.selesautomators.test.common.model

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val endDateAndTimeUTC: Long,
    val location: String,
    val status: TaskStatus,
    val createdAt: Long
) {

    companion object {
        val default: Task = Task(
            id = 1,
            title = "Title",
            description = "Description",
            endDateAndTimeUTC = 0,
            location = "Minsk",
            status = TaskStatus.IN_PROGRESS,
            createdAt = 0
        )
    }

}