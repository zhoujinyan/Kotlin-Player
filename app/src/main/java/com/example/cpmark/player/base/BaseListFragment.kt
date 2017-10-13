package com.example.cpmark.player.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.cpmark.player.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by cpMark on 2017/10/5.
 */
abstract class BaseListFragment<RESPONSE, ITEMBEAN, ITEMVIEW:View> : BaseFragment(), BaseView<RESPONSE> {

    val mAdapter by lazy {
        createSpecialAdapter()
    }

    val mPresenter by lazy {
        createSpecialPresenter()
    }

    override fun initFragmentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, null, false)
    }

    override fun initView() {
        rvHome.layoutManager = LinearLayoutManager(context)
        rvHome.adapter = mAdapter

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.loadData()
            swipeRefreshLayout.isRefreshing = false
        }

        rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = rvHome.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == mAdapter.itemCount - 1) {
                            mPresenter.loadData(mAdapter.itemCount)
                        }
                    }
                }
            }
        })
    }

    override fun onError(message: String?) {
        toast("加载失败$message")
    }

    override fun loadMoreData(response: RESPONSE?) {
        val dataList = getList(response)
        mAdapter.loadMoreData(dataList)
    }

    override fun loadSuccess(response: RESPONSE?) {
        val dataList = getList(response)
        mAdapter.updateData(dataList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.destroyView()
    }

    abstract fun getList(response: RESPONSE?): ArrayList<ITEMBEAN>?
    abstract fun createSpecialAdapter(): BaseListAdapter<ITEMBEAN, ITEMVIEW>
    abstract fun createSpecialPresenter(): BaseListPresenter
}