package com.example.cpmark.player.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cpmark.player.R
import com.itheima.player.model.bean.HomeItemBean
import kotlinx.android.synthetic.main.view_home_item.view.*

/**
 * Created by cpMark on 2017/9/30.
 */
class HomeItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_home_item,this)
    }

    fun updateView(homeItemBean: HomeItemBean) {
        tvSongName.text = homeItemBean.title
        tvSingerName.text = homeItemBean.description
        Glide
                .with(context.applicationContext)
                .load(homeItemBean.posterPic)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivBackground)
    }
}