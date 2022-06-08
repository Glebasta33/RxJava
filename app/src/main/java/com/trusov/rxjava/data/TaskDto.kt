package com.trusov.rxjava.data

data class TaskDto(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)