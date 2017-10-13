package com.example.cpmark.player.net

/**
 * Created by cpMark on 2017/10/4.
 * 结果回调
 */
interface ResultHanlder<RESPONSE> {

    fun onError(type:Int,msg:String?)

    fun onResponse(type:Int,result:RESPONSE)
}