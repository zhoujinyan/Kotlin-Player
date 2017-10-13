package com.example.cpmark.player.util

import com.itheima.player.model.LyricBean
import java.io.File
import java.nio.charset.Charset
import java.util.*


/**
 * ClassName:LyricUtil
 * Description:歌词解析util
 */
object LyricUtil {
    /**
     * 解析歌词文件获取歌词集合
     */
    fun parseLyric(file:File):List<LyricBean>{
        //创建集合
        val list = ArrayList<LyricBean>()
        //判断歌词是否为空
        if(!file.exists()){
            list.add(LyricBean(0,"歌词加载错误"))
            return list
        }
        //解析歌词文件 添加到集合中
        val linesList = file.readLines(Charset.forName("gbk"))//读取歌词文件 返回每一行数据集合
        linesList
                .map { //解析一行
                    parseLine(it)
                    //添加到集合中
                }
                .forEach { list.addAll(it) }
        //歌词排序
        list.sortBy { it.startTime }
        //返回集合
        return list
    }

    /**
     * 解析一行歌词
     * [01:33.67 [02:46.87 伤心的泪儿谁来擦
     */
    private fun parseLine(line: String): List<LyricBean> {
        //创建集合
        //解析当前行
        val arr = line.split("]")
        //获取歌词内容
        val content = arr[arr.size-1]
        //返回集合
        return (0 until arr.size-1)
                .map { parseTime(arr[it]) }
                .map { LyricBean(it,content) }
    }

    /**
     * 解析时间
     * 01 01 33.67
     */
    private fun parseTime(get: String): Int {
        //[去掉
        val timeS = get.substring(1)
        //按照:切割
        val list = timeS.split(":")
        var hour = 0
        var min = 0
        var sec = 0f
        if(list.size==3){
            //小时
            hour = (list[0].toInt())*60*60*1000
            min = (list[1].toInt())*60*1000
            sec = (list[2].toFloat())*1000
        }else{
            //没有小时
            min = (list[0].toInt())*60*1000
            sec = (list[1].toFloat())*1000
        }
        return (hour+min+sec).toInt()
    }
}