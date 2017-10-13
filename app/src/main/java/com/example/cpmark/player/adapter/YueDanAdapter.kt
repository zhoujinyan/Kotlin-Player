package com.example.cpmark.player.adapter

import android.content.Context
import android.view.View
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.widget.YueDanItemView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by cpMark on 2017/9/30.
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean,YueDanItemView>() {
    override fun createItemView(context: Context?): View {
        return YueDanItemView(context)
    }

    override fun updateView(itemView: YueDanItemView, itembean: YueDanBean.PlayListsBean) {
        itemView.updateView(itembean)
    }
}