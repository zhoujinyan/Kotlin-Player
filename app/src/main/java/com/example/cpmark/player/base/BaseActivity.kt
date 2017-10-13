package com.example.cpmark.player.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by cpMark on 2017/9/29.
 */
abstract class BaseActivity : AppCompatActivity(),AnkoLogger{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutId())
        initView()
        initData()
        initListener()
    }

    open fun initView(){

    }

    open fun initListener() {
    }

    open fun initData() {
    }

    abstract fun initLayoutId(): Int

    /**
     * 封装吐司，保证在UI线程
     */
    open fun mainThreadToast(message :String){
        runOnUiThread {
            toast(message)
        }
    }

    /**
     * 启动一个Activity并finish当前界面，Kotlin中泛型需要指定泛型的基本类型
     */
    inline fun <reified T:BaseActivity>startActivityAndFinish(){
        startActivity<T>()
        finish()
    }

}