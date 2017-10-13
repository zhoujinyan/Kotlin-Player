package com.itheima.player.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.cpmark.player.R
import com.itheima.player.model.AudioBean
import kotlinx.android.synthetic.main.view_vbang_item.view.*


/**
 * ClassName:VbangItemView
 * Description:
 */
class VbangItemView:RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_vbang_item,this)
    }

    /**
     * view+data绑定
     */
    fun setData(itemBean: AudioBean) {
        //歌曲名
        title.text = itemBean.display_name
        //歌手名
        artist.text = itemBean.artist
        //歌曲大小
        size.text = Formatter.formatFileSize(context,itemBean.size)
    }
}