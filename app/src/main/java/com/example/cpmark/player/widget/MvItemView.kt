package com.example.cpmark.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cpmark.player.R
import com.itheima.player.model.bean.HomeItemBean
import com.itheima.player.model.bean.VideosBean
import kotlinx.android.synthetic.main.view_mv_item.view.*

/**
 * Created by cpMark on 2017/9/30.
 */
class MvItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_mv_item, this)
    }

    fun updateView(videoItemBean: VideosBean) {
        tvSongName.text = videoItemBean.title
        tvSingerName.text = videoItemBean.artistName
        Glide.with(context.applicationContext)
                .load(videoItemBean.playListPic)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivBackground)
    }
}