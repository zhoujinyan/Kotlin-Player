package com.example.cpmark.player.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import com.example.cpmark.player.R
import com.example.cpmark.player.ui.activity.SettingsActivity


/**
 * ClassName:ToolbarManager
 * Description:所有界面toolbar的管理类
 */
interface ToolbarManager {
    val mToolbar: Toolbar
    /**
     * 初始化主界面中的toolbar
     */
    fun initToolbar() {
        mToolbar.setTitle("Kotlin影音")
        mToolbar.inflateMenu(R.menu.main)
        //kotlin 和java调用特性
        //如果java接口中只有一个未实现的方法  可以省略接口对象 直接用{}表示未实现的方法
        mToolbar.setOnMenuItemClickListener {
            mToolbar.context.startActivity(Intent(mToolbar.context, SettingsActivity::class.java))
            true
        }
    }

    /**
     * 处理设置界面的toolbar
     */
    fun initSettingsToolbar(){
        mToolbar.setTitle("设置界面")
    }

}