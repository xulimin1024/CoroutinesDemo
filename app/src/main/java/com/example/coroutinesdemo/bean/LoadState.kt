package com.example.coroutinesdemo.bean

//网络加载状态信息
sealed class LoadState(val msg:String) {
    class Loading(msg: String = "") : LoadState(msg)
    class Success(msg: String = "") : LoadState(msg)
    class Fail(msg: String) : LoadState(msg)
}