package com.example.cpmark.player.net

import com.example.cpmark.player.util.ThreadUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

/**
 * Created by cpMark on 2017/10/4.
 * 单例模式--网络请求管理者
 */
object NetManager {

    private val mOkHttpClient by lazy {
        OkHttpClient()
    }


    fun <RESPONSE>sendRequest(mRequest:MRequest<RESPONSE>){
        val request = Request.Builder()
                .url(mRequest.url)
                .get()
                .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    mRequest.hanlder.onError(mRequest.type,e?.message)
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = mRequest.parseResult(response?.body()?.string())
                ThreadUtil.runOnMainThread(Runnable {
                    mRequest.hanlder.onResponse(mRequest.type,result)
                })
            }

        })
    }
}