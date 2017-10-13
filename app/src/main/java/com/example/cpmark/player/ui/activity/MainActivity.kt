package com.example.cpmark.player.ui.activity

import android.support.v7.widget.Toolbar
import com.example.cpmark.player.R
import com.example.cpmark.player.base.BaseActivity
import com.example.cpmark.player.util.FragmentUtil
import com.example.cpmark.player.util.ToolbarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(),ToolbarManager {

    override val mToolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initToolbar()
    }

    override fun initListener() {
        bottomBar.setOnTabSelectListener { tabId ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, FragmentUtil.fragmentUtil.getFragment(tabId),tabId.toString()).commit()
        }
    }

}
