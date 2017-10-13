package com.example.cpmark.player.ui.fragment

import com.example.cpmark.player.adapter.HomeAdapter
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.base.BaseListFragment
import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.presenter.impl.HomePresenterImpl
import com.example.cpmark.player.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by cpMark on 2017/9/30.
 */
class HomeFragment :BaseListFragment<ArrayList<HomeItemBean>,HomeItemBean,HomeItemView>(){
    override fun getList(response: ArrayList<HomeItemBean>?): ArrayList<HomeItemBean>? {
        return response
    }

    override fun createSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun createSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

}