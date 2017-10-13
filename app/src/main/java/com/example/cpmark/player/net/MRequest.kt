package com.example.cpmark.player.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

/**
 * Created by cpMark on 2017/10/4.
 */
open class MRequest<RESPONSE>(val type: Int,val url:String,val hanlder:ResultHanlder<RESPONSE>) {

    /**
     * 利用反射获取泛型实际类型
     */
    fun parseResult(result:String?):RESPONSE{
        val type = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return Gson().fromJson<RESPONSE>(result, type)
    }

    fun execute() {
        NetManager.sendRequest(this)
    }

}