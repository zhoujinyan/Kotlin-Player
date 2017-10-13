package com.example.cpmark.player.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.example.cpmark.player.R
import com.example.cpmark.player.ui.activity.AudioPlayActivity
import com.example.cpmark.player.ui.activity.MainActivity
import com.itheima.player.model.AudioBean
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by cpMark on 2017/10/8.
 */
class AudioService : Service() {

    val TAG_MODE = "MODE"

    val TYPE = "TYPE"

    companion object {
        val MODE_ALL = 0
        val MODE_SINGLE = 1
        val MODE_RANDOM = 2
    }

    private val REQUEST_NOTIFY = 1000
    private val REQUEST_PRE = 1001
    private val REQUEST_STATE = 1002
    private val REQUEST_NEXT = 1003

    var mCurrentMode = MODE_ALL

    var mMediaPlayer: MediaPlayer? = null

    val mDataList by lazy {
        ArrayList<AudioBean>()
    }

    var mPosition = -2

    private val mBinder by lazy {
        AudioBinder()
    }

    val mSp by lazy {
        getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    private var mNotification: Notification? = null

    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        mCurrentMode = mSp.getInt(TAG_MODE, MODE_ALL)
    }

    /**
     * 重复开启服务，会重复调用
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            val type = it.getIntExtra(TYPE, -1)
            when (type) {
                REQUEST_NOTIFY -> {
                    mBinder.updatePlayUI()
                }
                REQUEST_PRE -> {
                    mBinder.playPre()
                }
                REQUEST_STATE -> {
                    mBinder.updatePlayState()
                }
                REQUEST_NEXT -> {
                    mBinder.playNext()
                }
                else -> {
                    val dataList = it.getParcelableArrayListExtra<AudioBean>("list")
                    if (dataList != null && dataList.size > 0) {
                        mDataList.clear()
                        mDataList.addAll(dataList)
                    }
                    val position = it.getIntExtra("position", -1)
                    if (position != mPosition) {
                        mPosition = position
                        mBinder.playAudio()
                    } else {
                        mBinder.updatePlayUI()
                    }
                }
            }
        }
        //非粘性，service意外销毁（不是代码调用stopService）不会重启服务
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    inner class AudioBinder : Binder(), IService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        override fun playSpecified(position: Int) {
            mPosition = position
            playAudio()
        }

        override fun getPlayList(): ArrayList<AudioBean> {
            return mDataList
        }

        override fun playNext() {
            autoPlayNext()
        }

        override fun playPre() {
            if (mDataList.size <= 0) return
            when (mCurrentMode) {
                MODE_ALL -> {
                    mPosition = if (mPosition == 0) {
                        mDataList.size - 1
                    } else {
                        --mPosition
                    }
                }

                MODE_RANDOM -> {
                    mPosition = Random().nextInt(mDataList.size)
                }
            }

            playAudio()
        }

        override fun getPlayMode(): Int {
            return mCurrentMode
        }

        override fun updateMode() {
            when (mCurrentMode) {
                MODE_ALL -> mCurrentMode = MODE_SINGLE
                MODE_SINGLE -> mCurrentMode = MODE_RANDOM
                MODE_RANDOM -> mCurrentMode = MODE_ALL
            }

            mSp.edit().putInt(TAG_MODE, mCurrentMode).commit()
        }

        override fun onCompletion(p0: MediaPlayer?) {
            autoPlayNext()
        }

        private fun autoPlayNext() {
            when (mCurrentMode) {
                MODE_ALL -> {
                    if (mDataList.size > 0)
                        mPosition = (mPosition + 1) % mDataList.size
                }

                MODE_RANDOM -> {
                    if (mDataList.size > 0)
                        mPosition = Random().nextInt(mDataList.size)
                }
            }

            playAudio()
        }

        override fun seekTo(progress: Int) {
            mMediaPlayer?.seekTo(progress)
        }

        override fun getRealTimeProgress(): Int {
            return mMediaPlayer?.currentPosition ?: 0
        }

        override fun getTotalProgress(): Int {
            return mMediaPlayer?.duration ?: 0
        }

        override fun updatePlayState() {
            val playing = isPlaying()
            playing?.let {
                if (playing) {
                    //当前为播放状态，进入到暂停状态
                    pause()
                } else {
                    //当前为暂停状态，进入到播放状态
                    start()
                }
            }
        }

        private fun pause() {
            mMediaPlayer?.pause()
            updatePlayUI()
            mNotification?.let {
                mNotification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
                mNotificationManager.notify(REQUEST_NOTIFY, mNotification)
            }
        }

        private fun start() {
            mMediaPlayer?.start()
            updatePlayUI()
            mNotification?.let {
                mNotification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
                mNotificationManager.notify(REQUEST_NOTIFY, mNotification)
            }
        }


        override fun isPlaying(): Boolean? {
            return mMediaPlayer?.isPlaying
        }

        override fun onPrepared(p0: MediaPlayer?) {
            start()
            updatePlayUI()
            //显示通知
            showNotification()
        }


        fun updatePlayUI() {
            EventBus.getDefault().post(mDataList[mPosition])
        }

        fun playAudio() {

            if (mMediaPlayer != null) {
                mMediaPlayer?.reset()
                mMediaPlayer?.release()
                mMediaPlayer = null
            }

            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.setDataSource(mDataList[mPosition]?.data)
            mMediaPlayer?.prepareAsync()
        }
    }

    private fun showNotification() {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotification = getNotification()
        mNotificationManager.notify(REQUEST_NOTIFY, mNotification)
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this@AudioService)
                //设置通知小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知时的文本提示
                .setTicker("当前正在播放歌曲${mDataList[mPosition].display_name}")
                //设置下拉时，通知的内容View
                .setCustomContentView(getRomoteView())
                //设置通知的显示时间
                .setWhen(System.currentTimeMillis())
                //设置不能滑动删除通知
                .setOngoing(true)
                //设置点击通知主题时的点击事件
                .setContentIntent(getPendingIntent())
                .build()
    }

    private fun getPendingIntent(): PendingIntent? {
        val intentM = Intent(this@AudioService, MainActivity::class.java)
        val intentA = Intent(this@AudioService, AudioPlayActivity::class.java)
        intentA.putExtra(TYPE, REQUEST_NOTIFY)
        val intents = arrayOf(intentM, intentA)
        return PendingIntent.getActivities(this@AudioService, REQUEST_NOTIFY, intents, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getRomoteView(): RemoteViews? {
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        //修改Notification内容视图的文本信息
        remoteViews.setTextViewText(R.id.title, mDataList[mPosition].display_name)
        remoteViews.setTextViewText(R.id.artist, mDataList[mPosition].artist)
        //设置Notification图标上的点击事件
        remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
        remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
        remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())

        return remoteViews
    }

    private fun getNextPendingIntent(): PendingIntent? {
        val intent = Intent(this@AudioService, AudioService::class.java)
        intent.putExtra(TYPE, REQUEST_NEXT)
        return PendingIntent.getService(this@AudioService, REQUEST_NEXT, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getStatePendingIntent(): PendingIntent? {
        val intent = Intent(this@AudioService, AudioService::class.java)
        intent.putExtra(TYPE, REQUEST_STATE)
        return PendingIntent.getService(this@AudioService, REQUEST_STATE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getPrePendingIntent(): PendingIntent? {
        val intent = Intent(this@AudioService, AudioService::class.java)
        intent.putExtra(TYPE, REQUEST_PRE)
        return PendingIntent.getService(this@AudioService, REQUEST_PRE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}