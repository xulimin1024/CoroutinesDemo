package com.example.coroutinesdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.bean.LoadState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    //声明一个viewmodel
    lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取viewmodel对象
        viewModel  = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //对加载状态进行动态观察
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success -> button.isEnabled = true
                is LoadState.Fail -> {
                    button.isEnabled = true
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
                is LoadState.Loading -> {
                    button.isEnabled = false
                }
            }

        })

        viewModel.imageData.observe(this, Observer {
            //用Glide加载三张图片
            Glide.with(this).load(it[0]).into(imageView1)
            Glide.with(this).load(it[1]).into(imageView2)
            Glide.with(this).load(it[2]).into(imageView3)
        })
        //点击刷新按钮来网络加载
        button.setOnClickListener {
            viewModel.getData()
        }

    }

}