package com.uogames.salesautomators.test.database.mapper

import com.uogames.salesautomators.test.database.entity.TaskEntity
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus

object TaskMapper {


    fun TaskEntity.toModel() = Task(
        id = id,
        title = title,
        description = description,
        endDateAndTimeUTC = endDateAndTime,
        location = location,
        status = TaskStatus.entries[status],
        createdAt = createdAd
    )

    fun Task.toEntity() = TaskEntity(
        id = id,
        title = title,
        description = description,
        endDateAndTime = endDateAndTimeUTC,
        location = location,
        status = status.ordinal,
        createdAd = createdAt
    )


}