package com.example.cpmark.player.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.example.cpmark.player.R
import com.example.cpmark.player.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by cpMark on 2017/9/29.
 */
class SplashActivity :BaseActivity(), ViewPropertyAnimatorListener {

    override fun initLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initListener() {
        ViewCompat.animate(ivSplash).scaleX(1.0f).scaleY(1.0f).setListener(this).duration = 1500
    }

    override fun onAnimationEnd(view: View?) {
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }
}