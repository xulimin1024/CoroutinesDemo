package com.example.coroutinesdemo.network

import com.example.coroutinesdemo.bean.ImageDataResponseBody2
import com.example.coroutinesdemo.bean.ResponseBody
import com.example.coroutinesdemo.bean.Result
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//网络层访问统一入口
object NetworkService {
    //retorfit实例，在这里做一些统一网络配置，如添加转换器、设置超时时间等
    private val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().callTimeout(5, java.util.concurrent.TimeUnit.SECONDS).build())
        .baseUrl("https://api.apiopen.top/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //网络层访问服务
    val apiService = retrofit.create<ApiService>()

    //数据脱壳与错误预处理
    fun <T> preprocessData(responseBody: ResponseBody<T>): T {
        return if (responseBody.code == 200) responseBody.result else throw Throwable(responseBody.msg)
    }
    //直接把该方法暴露给viemodel
    suspend fun getImageData(): List<Result> {
        //调用ApiService定义的接口方法
        val responseBody = apiService.getImages2()
        //返回处理后的数据
        return preprocessData<List<Result>>(responseBody)
    }
}