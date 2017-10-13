package com.example.cpmark.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cpmark.player.R
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by cpMark on 2017/9/30.
 */
class HomeLoadMoreView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_home_load_more, this)
    }

    fun updateView(homeItemBean: HomeItemBean) {
    }
}