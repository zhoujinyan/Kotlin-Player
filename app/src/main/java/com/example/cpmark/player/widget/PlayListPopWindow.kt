package com.example.cpmark.player.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.cpmark.player.R
import org.jetbrains.anko.find

/**
 * Created by cpMark on 2017/10/9.
 */
class PlayListPopWindow(context: Context,adapter:BaseAdapter,val mWindow:Window,listener:AdapterView.OnItemClickListener) : PopupWindow() {

    private var mAlpha = 0f

    init {
        mAlpha = mWindow.attributes.alpha
        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        contentView = view
        //设置宽高
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        height = point.y * 3 / 5

        val listView = view.find<ListView>(R.id.listView)
        listView.adapter = adapter
        listView.onItemClickListener = listener

        //设置获取焦点
        isFocusable = true
        //设置可点击外部dismiss
        isOutsideTouchable = true
        //为了在低版本上也能正常响应back事件，添加一个background
        setBackgroundDrawable(ColorDrawable())

        animationStyle = R.style.pop
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val attributes = mWindow.attributes
        attributes.alpha = 0.4f
        mWindow.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        val attributes = mWindow.attributes
        attributes.alpha = mAlpha
        mWindow.attributes = attributes
    }
}