package com.example.cpmark.player.adapter

import android.content.Context
import android.view.View
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.widget.MvItemView
import com.example.cpmark.player.widget.YueDanItemView
import com.itheima.player.model.bean.VideosBean
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by cpMark on 2017/9/30.
 */
class MvItemAdapter : BaseListAdapter<VideosBean, MvItemView>() {
    override fun createItemView(context: Context?): View {
        return MvItemView(context)
    }

    override fun updateView(itemView: MvItemView, itembean: VideosBean) {
        itemView.updateView(itembean)
    }
}