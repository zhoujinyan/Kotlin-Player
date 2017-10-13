package com.example.cpmark.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cpmark.player.R
import com.itheima.player.model.bean.YueDanBean
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.view_yue_dan_item.view.*

/**
 * Created by cpMark on 2017/9/30.
 */
class YueDanItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_yue_dan_item, this)
    }

    fun updateView(playListsBean: YueDanBean.PlayListsBean) {
        tvTitle.text = playListsBean.title
        tvAuthor.text = playListsBean.creator?.nickName
        Glide.with(context.applicationContext)
                .load(playListsBean.creator?.largeAvatar)
                .bitmapTransform(CropCircleTransformation(context))
                .into(ivAuthorAvatar)
        Glide.with(context.applicationContext)
                .load(playListsBean.playListBigPic)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivBackground)
    }
}