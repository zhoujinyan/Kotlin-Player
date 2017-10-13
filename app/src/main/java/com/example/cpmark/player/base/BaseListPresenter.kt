package com.example.cpmark.player.base

/**
 * Created by cpMark on 2017/10/4.
 */
interface BaseListPresenter {

    companion object {
        val TYPE_INIT_OR_REFRESH = 0
        val TYPE_LOAD_MORE = 1
    }

    /**
     * 初始化加载/下拉舒心
     */
    fun loadData()

    /**
     * 加载更多
     */
    fun loadData(offset:Int)

    /**
     * 解除和V层的绑定
     */
    fun destroyView()
}