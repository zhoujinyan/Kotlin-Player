package com.example.cpmark.player.service

import com.itheima.player.model.AudioBean

/**
 * Created by cpMark on 2017/10/8.
 */
interface IService {
    /**
     * 更新播放状态
     */
    fun updatePlayState()

    /**
     * 获取当前播放状态
     */
    fun isPlaying():Boolean?

    /**
     * 获取播放总进度
     */
    fun getTotalProgress():Int

    /**
     * 获取实时播放进度
     */
    fun getRealTimeProgress(): Int

    /**
     * 拖动到相应进度
     */
    fun seekTo(progress: Int)

    /**
     * 更新播放模式
     */
    fun updateMode()

    /**
     * 获取当前播放模式
     */
    fun getPlayMode():Int

    /**
     * 播放下一首
     */
    fun playNext()

    /**
     * 播放上一首
     */
    fun playPre()

    /**
     * 获取播放列表数据
     */
    fun getPlayList(): ArrayList<AudioBean>

    /**
     * 播放指定位置的歌曲
     */
    fun playSpecified(position: Int)
}