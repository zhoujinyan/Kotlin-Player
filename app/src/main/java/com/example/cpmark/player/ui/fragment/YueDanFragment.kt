package com.example.cpmark.player.ui.fragment

import com.example.cpmark.player.adapter.YueDanAdapter
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.base.BaseListFragment
import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.presenter.impl.YueDanPresenterImpl
import com.example.cpmark.player.widget.YueDanItemView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by cpMark on 2017/9/30.
 */
class YueDanFragment : BaseListFragment<YueDanBean,YueDanBean.PlayListsBean,YueDanItemView>() {
    override fun getList(response: YueDanBean?): ArrayList<YueDanBean.PlayListsBean>? {
        return ArrayList(response?.playLists)
    }

    override fun createSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun createSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

}