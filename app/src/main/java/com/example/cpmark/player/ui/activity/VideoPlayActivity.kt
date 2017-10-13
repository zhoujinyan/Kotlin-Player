package com.example.cpmark.player.ui.activity

import cn.jzvd.JZVideoPlayerStandard
import com.example.cpmark.player.R
import com.example.cpmark.player.base.BaseActivity
import com.itheima.player.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_play.*
import cn.jzvd.JZVideoPlayer


/**
 * Created by cpMark on 2017/10/5.
 */
class VideoPlayActivity : BaseActivity() {
    override fun initLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initData() {
        val data = intent.data
        if (data == null) {
            //说明是应用内跳转
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            videoplayer.setUp(videoPlayBean.url, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoPlayBean.title)
        } else {
            if (data.toString().startsWith("http")) {
                //应用外的网络视频
                videoplayer.setUp(data.toString(), JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.toString())
            } else {
                //应用外的本地视频
                videoplayer.setUp(data.path, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.path)
            }
        }
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}