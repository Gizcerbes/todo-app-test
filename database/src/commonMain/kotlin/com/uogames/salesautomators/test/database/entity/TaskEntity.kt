package com.uogames.salesautomators.test.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("end_date_and_time")
    val endDateAndTime: Long,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("status")
    val status: Int,
    @ColumnInfo("created_at")
    val createdAd: Long
)
