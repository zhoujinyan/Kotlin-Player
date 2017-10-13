package com.example.cpmark.player.presenter.impl

import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.base.BaseView
import com.example.cpmark.player.net.HomeRequest
import com.example.cpmark.player.net.ResultHanlder
import com.example.cpmark.player.presenter.interf.HomePresenter
import com.example.cpmark.player.util.ThreadUtil
import com.example.cpmark.player.util.URLProviderUtils
import com.example.cpmark.player.view.HomeView
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by cpMark on 2017/10/4.
 */
class HomePresenterImpl(var homeView: BaseView<ArrayList<HomeItemBean>>?) : HomePresenter,ResultHanlder<ArrayList<HomeItemBean>> {

    override fun destroyView() {
        if(homeView != null){
            homeView = null
        }
    }

    override fun onError(type:Int,msg: String?) {
        homeView?.onError(msg)
    }

    override fun onResponse(type:Int,result: ArrayList<HomeItemBean>) {
        when(type){
            BaseListPresenter.TYPE_INIT_OR_REFRESH ->  homeView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> homeView?.loadMoreData(result)
        }

    }

    override fun loadData() {
        HomeRequest( BaseListPresenter.TYPE_INIT_OR_REFRESH,0, this).execute()
    }

    override fun loadData(offset: Int) {
        HomeRequest( BaseListPresenter.TYPE_LOAD_MORE,offset, this).execute()
    }

}