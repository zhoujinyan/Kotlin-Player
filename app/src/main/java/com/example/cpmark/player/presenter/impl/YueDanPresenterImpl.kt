package com.example.cpmark.player.presenter.impl

import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.base.BaseView
import com.example.cpmark.player.net.HomeRequest
import com.example.cpmark.player.net.ResultHanlder
import com.example.cpmark.player.net.YueDanRequest
import com.example.cpmark.player.presenter.interf.HomePresenter
import com.example.cpmark.player.presenter.interf.YueDanPresenter
import com.example.cpmark.player.view.YueDanView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by cpMark on 2017/10/4.
 */
class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResultHanlder<YueDanBean> {

    override fun destroyView() {
        if(yueDanView != null){
            yueDanView = null
        }
    }

    override fun onError(type:Int,msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onResponse(type:Int,result: YueDanBean) {
        when(type){
            BaseListPresenter.TYPE_INIT_OR_REFRESH ->  yueDanView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> yueDanView?.loadMoreData(result)
        }

    }

    override fun loadData() {
        YueDanRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadData(offset: Int) {
        YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
    }

}