package com.example.cpmark.player.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.toast

/**
 * Created by cpMark on 2017/9/29.
 */
abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initFragmentView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    abstract fun initFragmentView(): View

    open fun init() {
    }

    /**
     * 封装吐司，保证在UI线程
     */
    fun mainThreadToast(message: String) {
        activity.runOnUiThread {
            toast(message)
        }
    }
}

