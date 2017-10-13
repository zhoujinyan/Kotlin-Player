package com.example.cpmark.player.adapter

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import com.itheima.player.model.AudioBean
import com.itheima.player.widget.VbangItemView

/**
 * Created by cpMark on 2017/10/6.
 */
class VBangAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor) {
    /**
     * 创建ItemView
     */
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? {
        return VbangItemView(context)
    }

    /**
     * 绑定数据
     */
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        if(view is VbangItemView){
            view.setData(AudioBean.getAudioBean(cursor))
        }
    }
}