package com.example.cpmark.player.ui.activity

import android.support.v7.widget.Toolbar
import com.example.cpmark.player.R
import com.example.cpmark.player.base.BaseActivity
import com.example.cpmark.player.util.ToolbarManager
import org.jetbrains.anko.find

/**
 * Created by cpMark on 2017/9/30.
 */
class SettingsActivity : BaseActivity(),ToolbarManager {
    override val mToolbar by lazy{
        find<Toolbar>(R.id.toolbar)
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_settings
    }

    override fun initData() {
        initSettingsToolbar()
    }
}