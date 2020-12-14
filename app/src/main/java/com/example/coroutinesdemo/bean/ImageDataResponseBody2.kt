package com.example.coroutinesdemo.bean

//定义的数据类
data class ImageDataResponseBody2(
    val code: Int,
    val message: String,
    val result: List<Result>
)

data class Result(
    val id: Int,
    val img: String,
    val time: String
)