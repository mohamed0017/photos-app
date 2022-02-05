package com.task.koinztask.data.mapper

interface Mapper<in D, out V> {
    fun map(d : D) : V
}