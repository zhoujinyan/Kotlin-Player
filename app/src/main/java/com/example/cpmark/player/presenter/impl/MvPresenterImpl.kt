package com.example.cpmark.player.presenter.impl

import com.example.cpmark.player.net.MvRequest
import com.example.cpmark.player.net.ResultHanlder
import com.example.cpmark.player.presenter.interf.MvPresenter
import com.example.cpmark.player.view.MvView
import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by cpMark on 2017/10/5.
 */
class MvPresenterImpl(var mvView: MvView?) : MvPresenter, ResultHanlder<ArrayList<MvAreaBean>> {
    override fun onResponse(type: Int, result: ArrayList<MvAreaBean>) {
        mvView?.loadSuccess(result)
    }

    override fun destroyView() {
        if (mvView != null) {
            mvView = null
        }
    }

    override fun onError(type: Int, msg: String?) {
        mvView?.onError(msg)
    }

    override fun loadData() {
        MvRequest(-1,this).execute()
    }
}