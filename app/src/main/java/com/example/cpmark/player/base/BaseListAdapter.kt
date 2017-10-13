package com.example.cpmark.player.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.cpmark.player.widget.HomeLoadMoreView

/**
 * Created by cpMark on 2017/10/4.
 */
abstract class BaseListAdapter<ITEMBEAN,ITEMVIEW:View> : RecyclerView.Adapter<BaseListAdapter.BaseListViewHolder>() {

    val TYPE_NORMAL = 0
    val TYPE_LOAD_MORE = 1

    val mDataList by lazy { ArrayList<ITEMBEAN>() }

    fun updateData(dataList: ArrayList<ITEMBEAN>?) {
        dataList?.let {
            mDataList.clear()
            mDataList.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    fun loadMoreData(dataList: ArrayList<ITEMBEAN>?) {
        dataList?.let {
            mDataList.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: BaseListViewHolder?, position: Int) {

        if (getItemViewType(position) != TYPE_LOAD_MORE) {
            val itemView = holder?.itemView as ITEMVIEW
            updateView(itemView,mDataList[position])
            itemView.setOnClickListener {
                //在mListener不为null的时候调用
                mListener?.invoke(mDataList[position])
            }
        }
    }

    var mListener:((itemBean:ITEMBEAN)->Unit)? = null

    fun setItemListener(listener:(itemBean:ITEMBEAN)->Unit){
        mListener = listener
    }

    abstract fun updateView(itemView: ITEMVIEW, itembean: ITEMBEAN)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseListViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> BaseListViewHolder(createItemView(parent?.context))
            else -> BaseListViewHolder(HomeLoadMoreView(parent?.context))
        }
    }

    abstract fun createItemView(context: Context?): View

    override fun getItemCount(): Int {
        return mDataList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            mDataList.size -> TYPE_LOAD_MORE
            else -> TYPE_NORMAL
        }
    }


    class BaseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}