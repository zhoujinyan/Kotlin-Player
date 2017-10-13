package com.example.cpmark.player.adapter

import android.content.Context
import android.view.View
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by cpMark on 2017/9/30.
 */
class HomeAdapter :BaseListAdapter<HomeItemBean,HomeItemView>() {
    override fun createItemView(context: Context?): View {
        return HomeItemView(context)
    }

    override fun updateView(itemView: HomeItemView, itembean: HomeItemBean) {
        itemView.updateView(itembean)
    }
}