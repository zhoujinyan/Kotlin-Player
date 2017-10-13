package com.example.cpmark.player.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.cpmark.player.R
import com.example.cpmark.player.util.LyricLoader
import com.example.cpmark.player.util.LyricUtil
import com.itheima.player.model.LyricBean
import org.jetbrains.anko.doAsync

/**
 * Created by cpMark on 2017/10/11.
 */
class LyricView : View {

    private var mSmallSize = 0.0f
    private var mBigSize = 0.0f
    private var mWhite = 0
    private var mGreen = 0
    //当前界面显示的最多歌词行数
    private var mCenterLine = 0
    private var mLineHeight = resources.getDimensionPixelOffset(R.dimen.lyric_line_height)
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mTextWidth = 0
    private var mTextHeight = 0
    private var mDuration = 0
    private var mRealProgress = 0
    //手指按下时，y轴坐标
    private var mDownY = 0f
    //手指按下时，歌词已经偏离中心的距离
    private var mMarkY = 0f
    //歌词偏移量
    private var mOffsetY = 0f

    //是否根据进程更新歌词
    private var mUpdateByProgress = true

    private val mPaint by lazy {
        //抗锯齿
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val mDataList by lazy {
        ArrayList<LyricBean>()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mSmallSize = resources.getDimension(R.dimen.small_size)
        mBigSize = resources.getDimension(R.dimen.big_size)
        mWhite = resources.getColor(R.color.white)
        mGreen = resources.getColor(R.color.green)

        //设置文本画笔x方向位置确定以文本中心为参考点
        mPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mDataList.size == 0) {
            drawSingleLine(canvas)
        } else {
            drawMultiLine(canvas)
        }
    }

    /**
     * 绘制单行居中文本
     */
    private fun drawSingleLine(canvas: Canvas?) {
        canvas?.let {
            mPaint.textSize = mBigSize
            mPaint.color = mGreen

            val text = "正在播放歌曲。。。"
            val bounds = Rect()
            mPaint.getTextBounds(text, 0, text.length, bounds)
            mTextWidth = bounds.width()
            mTextHeight = bounds.height()
            it.drawText(text, (mViewWidth / 2 - mTextWidth / 2).toFloat(), (mViewHeight / 2 + mTextHeight / 2).toFloat(), mPaint)
        }
    }

    /**
     * 绘制多行居中文本
     */
    private fun drawMultiLine(canvas: Canvas?) {

        if (mUpdateByProgress) {

            //确定行可用时间
            val lineTime = if (mCenterLine == mDataList.size - 1) {
                //最后一行
                mDuration - mDataList[mDataList.size - 1].startTime
            } else {
                //其它行
                mDataList[mCenterLine + 1].startTime - mDataList[mCenterLine].startTime
            }

            //确定偏移时间
            val offsetTime = mRealProgress - mDataList[mCenterLine].startTime
            //确定便宜百分比
            val pesenter = offsetTime / lineTime.toFloat()
            //中心行便宜量
            mOffsetY = pesenter * mLineHeight
        }

        val centerText = mDataList[mCenterLine].content
        val bounds = Rect()
        mPaint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()

        //居中行y
        val centerY = mViewHeight / 2 + textH / 2 - mOffsetY
        for ((index, value) in mDataList.withIndex()) {

            if (index == mCenterLine) {
                //居中行
                mPaint.textSize = mBigSize
                mPaint.color = mGreen
            } else {
                //其它行
                mPaint.textSize = mSmallSize
                mPaint.color = mWhite
            }

            val curX = mViewWidth / 2
            val curY = centerY + (index - mCenterLine) * mLineHeight
            val curText = value.content

            if (curY < 0) continue
            if (curY > mViewHeight + mLineHeight) break

            canvas?.drawText(curText, curX.toFloat(), curY.toFloat(), mPaint)
        }
    }

    /**
     * layout执行之后回调
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
    }

    /**
     * 刷新歌词
     */
    fun updateLyric(progress: Int) {
        if (mDataList.size == 0) return
        if(!mUpdateByProgress) return

        mRealProgress = progress
        //确定居中行行数
        val finalTime = mDataList[mDataList.size - 1].startTime
        if (progress >= finalTime) {
            mCenterLine = mDataList.size - 1
        } else {

            (0 until mDataList.size - 1)
                    .filter { progress >= mDataList[it].startTime && progress < mDataList[it + 1].startTime }
                    .forEach { mCenterLine = it }

        }


        requestLayout() //当布局参数改变时刷新
//        invalidate()//调用onDraw
//        postInvalidate()//调用onDraw，可以在子线程
    }

    /**
     * 设置歌曲总时长
     */
    fun setLyricDuration(duration: Int) {
        mDuration = duration
    }

    /**
     * 设置歌词文件
     */
    fun setSongName(songName: String) {
        doAsync {
            mDataList.clear()
            mDataList.addAll(LyricUtil.parseLyric(LyricLoader.loadLyricFile(songName)))
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //消费事件
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mUpdateByProgress = false
                    mDownY = it.y
                    mMarkY = mOffsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = it.y
                    val offY = mDownY - y
                    mOffsetY = mMarkY + offY
                    if (Math.abs(mOffsetY) >= mLineHeight) {
                        val offsetLine = (mOffsetY / mLineHeight).toInt()
                        mCenterLine += offsetLine

                        //限制居中行，避免越界
                        if(mCenterLine < 0){
                            mCenterLine = 0
                        }else if(mCenterLine >= mDataList.size){
                            mCenterLine = mDataList.size - 1
                        }
                        mOffsetY %= mLineHeight

                        //只有中心行改变时才需要通知进度改变
                        mProgressListener?.invoke(mDataList[mCenterLine].startTime)
                    }

                    mDownY = y
                    mMarkY = mOffsetY
                    invalidate()
                }
                MotionEvent.ACTION_UP -> mUpdateByProgress = true
            }
        }
        return true
    }

    private var mProgressListener : ((progress:Int) -> Unit) ? = null
    fun setProgressListener(listener:(progress:Int) -> Unit){
        mProgressListener = listener
    }
}