package com.example.cpmark.player.base

/**
 * Created by cpMark on 2017/10/4.
 */
interface BaseView<RESPONSE> {
    fun onError(message: String?)
    fun loadMoreData(data: RESPONSE?)
    fun loadSuccess(data: RESPONSE?)
}