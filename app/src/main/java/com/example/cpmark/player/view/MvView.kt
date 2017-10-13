package com.example.cpmark.player.view

import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by cpMark on 2017/10/4.
 */
interface MvView{
    fun onError(message: String?)
    fun loadSuccess(data: ArrayList<MvAreaBean>?)
}