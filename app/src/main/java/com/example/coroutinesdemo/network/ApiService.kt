package com.example.coroutinesdemo.network

import com.example.coroutinesdemo.bean.ImageDataResponseBody2
import com.example.coroutinesdemo.bean.ResponseBody
import com.example.coroutinesdemo.bean.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("getImages?page=0&&count=1")
    suspend fun getImages(): ImageDataResponseBody2

    @GET("getImages?page=0&&count=10")
    suspend fun getImages2(): ResponseBody<List<Result>>
}