package com.example.cpmark.player.presenter.impl

import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.net.MvItemRequest
import com.example.cpmark.player.net.ResultHanlder
import com.example.cpmark.player.presenter.interf.MvItemPresenter
import com.example.cpmark.player.view.MvListView
import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean

/**
 * Created by cpMark on 2017/10/5.
 */
class MvItemPresenterImpl(val mCode:String,var mvListView:MvListView?):MvItemPresenter, ResultHanlder<MvPagerBean> {
    override fun onError(type: Int, msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onResponse(type: Int, result: MvPagerBean) {
        val dataList = ArrayList<VideosBean>(result.videos)
        when(type){
            BaseListPresenter.TYPE_INIT_OR_REFRESH ->  mvListView?.loadSuccess(dataList)
            BaseListPresenter.TYPE_LOAD_MORE -> mvListView?.loadMoreData(dataList)
        }
    }

    override fun loadData() {
        MvItemRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH,mCode,0,this).execute()
    }

    override fun loadData(offset: Int) {
        MvItemRequest(BaseListPresenter.TYPE_LOAD_MORE,mCode,offset,this).execute()
    }

    override fun destroyView() {
        if(mvListView != null){
            mvListView = null
        }
    }
}