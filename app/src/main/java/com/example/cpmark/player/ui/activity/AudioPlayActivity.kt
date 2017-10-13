package com.example.cpmark.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.cpmark.player.R
import com.example.cpmark.player.adapter.PlayListAdapter
import com.example.cpmark.player.base.BaseActivity
import com.example.cpmark.player.service.AudioService
import com.example.cpmark.player.service.IService
import com.example.cpmark.player.util.StringUtil
import com.example.cpmark.player.widget.PlayListPopWindow
import com.itheima.player.model.AudioBean
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by cpMark on 2017/10/7.
 */
class AudioPlayActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {

    private val mServiceConn by lazy {
        AudioServiceConn()
    }

    private val MSG_PROGRESS = 100

    var mTotalProgress = 0

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_PROGRESS -> updateRealTimeProgress()
            }
        }
    }

    private lateinit var mAnimationDrawable: AnimationDrawable

    lateinit var mIService: IService

    lateinit var mPopWindow : PlayListPopWindow

    override fun initLayoutId(): Int {
        return R.layout.activity_audio_play
    }

    override fun initView() {
        val drawable = audio_anim.drawable
        if (drawable is AnimationDrawable) {
            mAnimationDrawable = drawable
        }
    }

    override fun initData() {
        intent.setClass(this, AudioService::class.java)

        //为了实现可以调用Service中的方法，从而控制音乐播放，所以采用bind的方法绑定服务
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE)
        //为了实现退出app也可以播放采用start方法开启服务
        startService(intent)
    }

    override fun initListener() {
        state.setOnClickListener(this)
        back.setOnClickListener(this)
        progress_sk.setOnSeekBarChangeListener(this)
        mode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)

        lyricView.setProgressListener {
            mIService?.seekTo(it)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.state -> updateState()
            R.id.back -> finish()
            R.id.mode -> updatePlayMode()
            R.id.pre -> playPre()
            R.id.next -> playNext()
            R.id.playlist -> showPlayList()
        }
    }

    private fun showPlayList() {
        val adapter = PlayListAdapter(mIService.getPlayList())
        mPopWindow = PlayListPopWindow(this,adapter,window,this)
        mPopWindow.showAsDropDown(audio_player_bottom,0,-audio_player_bottom.height)
    }

    private fun playNext() {
        mIService.playNext()
    }

    private fun playPre() {
        mIService.playPre()
    }

    private fun updatePlayMode() {
        mIService.updateMode()
        updatePlayModeUI()
    }

    private fun updatePlayModeUI() {
        val playMode = mIService.getPlayMode()
        when(playMode){
            AudioService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
            AudioService.MODE_SINGLE ->  mode.setImageResource(R.drawable.selector_btn_playmode_single)
            AudioService.MODE_RANDOM ->  mode.setImageResource(R.drawable.selector_btn_playmode_random)
        }
    }

    private fun updateState() {
        //更新播放状态（功能）
        mIService.updatePlayState()
        //更新播放状态（UI）
        updatePlayUI()
    }

    private fun updatePlayUI() {
        val playing = mIService.isPlaying()
        playing?.let {
            if (playing) {
                state.setImageResource(R.drawable.selector_btn_audio_play)
                mAnimationDrawable.start()
                mHandler.sendEmptyMessage(MSG_PROGRESS)
            } else {
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                mAnimationDrawable.stop()
                mHandler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: AudioBean?) {
        event?.let {
            audio_title.text = event.display_name
            artist.text = event.artist
            updatePlayUI()
            updateProgress()
            updatePlayModeUI()
            lyricView.setSongName(event.display_name)
        }
    }

    private fun updateProgress() {
        updateTotalProgress()
        updateRealTimeProgress()
    }

    private fun updateRealTimeProgress() {
        val realTimeProgress = mIService.getRealTimeProgress()
        updateRealTimeUI(realTimeProgress)
        mHandler.sendEmptyMessage(MSG_PROGRESS)
    }

    private fun updateRealTimeUI(realTimeProgress: Int) {
        progress.text = StringUtil.parseDuration(realTimeProgress) + "/" + StringUtil.parseDuration(mTotalProgress)
        progress_sk.progress = realTimeProgress
        lyricView.updateLyric(realTimeProgress)
    }

    private fun updateTotalProgress() {
        mTotalProgress = mIService.getTotalProgress()
        progress_sk.max = mTotalProgress
        lyricView.setLyricDuration(mTotalProgress)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConn)
        EventBus.getDefault().unregister(this)
        mHandler.removeCallbacksAndMessages(null)
    }

    inner class AudioServiceConn : ServiceConnection {
        /**
         * 意外断开连接时调用
         */
        override fun onServiceDisconnected(p0: ComponentName?) {

        }


        /**
         * 连接服务的时候调用
         */
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mIService = p1 as IService
        }

    }

    /**
     * p0 seekbar
     * p1  当前进度
     * p2 true表示为手指拖动，false表示为代码修改
     */
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if(!p2) return
        mIService.seekTo(p1)
        updateRealTimeUI(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        mIService.playSpecified(p2)
        if(mPopWindow.isShowing){
            mPopWindow.dismiss()
        }
    }
}