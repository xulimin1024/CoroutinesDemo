package com.example.coroutinesdemo.ui

import android.app.assist.AssistStructure
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesdemo.bean.ImageDataResponseBody2
import com.example.coroutinesdemo.bean.LoadState
import com.example.coroutinesdemo.bean.Result
import com.example.coroutinesdemo.launch
import com.example.coroutinesdemo.network.NetworkService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    //存放三张图片的url数据
    val imageData = MutableLiveData<List<String>>()
    //存放10张图片的url数据
    val imageData2 = MutableLiveData<List<String>>()
    //存放网路加载状态信息
    val loadState = MutableLiveData<LoadState>()

    //从网络加载数据
    fun getData() {
//        Log.d("xulimin","开始获取数据")
//        viewModelScope.launch (CoroutineExceptionHandler { _, e ->
//            //加载失败的状态
//            Log.d("xulimin","错误信息是"+e.message)
//            loadState.value = LoadState.Fail(e.message ?: "加载失败")
//        }){
//            //更新加载状态
//            loadState.value = LoadState.Loading()
//            //并发请求三张图片的数据
//            val data1 = async { NetworkService.apiService.getImages() }
//            val data2 = async { NetworkService.apiService.getImages() }
//            val data3 = async { NetworkService.apiService.getImages() }
//            //通过为LiveData设置新的值来触发更新UI
//            imageData.value  = listOf(data1.await(), data2.await(), data3.await()).map { it.result[0].img }
//
//            //更新加载状态
//            loadState.value = LoadState.Success()
//
//        }
        launch({
            loadState.value = LoadState.Loading()
            val data1 = async { NetworkService.apiService.getImages() }
            val data2 = async { NetworkService.apiService.getImages() }
            val data3 = async { NetworkService.apiService.getImages() }
            //通过为LiveData设置新的值来触发更新UI
            imageData.value =
                listOf(data1.await(), data2.await(), data3.await()).map { it.result[0].img }
            //更新加载状态
            loadState.value = LoadState.Success()
        },
            {
                loadState.value = LoadState.Fail(it.message ?: "加载失败")
            },
            {
                Log.d("xulimin","数据获取成功")
            }
        )
    }

    fun getData2(){
        Log.d("xulimin","================")
        launch(
            {
                val imageList = async { NetworkService.getImageData()}
                val imageListData = imageList.await()
                imageData2.value = imageListData.map { it.img }
            },
            {
                loadState.value = LoadState.Fail(it.message ?: "加载失败")
            },
            {
                Log.d("xulimin","数据获取成功")
            }
        )
    }
}