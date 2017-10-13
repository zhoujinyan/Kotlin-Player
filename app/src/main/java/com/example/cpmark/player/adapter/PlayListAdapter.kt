package com.example.cpmark.player.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.cpmark.player.widget.PopListItemView
import com.itheima.player.model.AudioBean

/**
 * Created by cpMark on 2017/10/9.
 */
class PlayListAdapter(val mDataList: ArrayList<AudioBean>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var itemView = if (p1 == null) {
            PopListItemView(p2?.context)
        } else {
            p1 as PopListItemView
        }
        itemView.setData(mDataList.get(p0))
        return itemView
    }

    override fun getItem(p0: Int): Any {
        return mDataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mDataList.size
    }
}