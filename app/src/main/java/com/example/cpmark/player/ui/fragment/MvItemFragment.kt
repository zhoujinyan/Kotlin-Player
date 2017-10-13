package com.example.cpmark.player.ui.fragment

import android.content.Intent
import com.example.cpmark.player.adapter.MvItemAdapter
import com.example.cpmark.player.base.BaseListAdapter
import com.example.cpmark.player.base.BaseListFragment
import com.example.cpmark.player.base.BaseListPresenter
import com.example.cpmark.player.presenter.impl.MvItemPresenterImpl
import com.example.cpmark.player.ui.activity.VideoPlayActivity
import com.example.cpmark.player.view.MvListView
import com.example.cpmark.player.widget.MvItemView
import com.itheima.player.model.VideoPlayBean
import com.itheima.player.model.bean.VideosBean
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by cpMark on 2017/10/5.
 */
class MvItemFragment : BaseListFragment<ArrayList<VideosBean>, VideosBean, MvItemView>(), MvListView {

    lateinit var mCode: String

    override fun init() {
        mCode = arguments.getString("code")
    }

    override fun initData() {
        super.initData()
        mAdapter.setItemListener {
            val videoPlayBean = VideoPlayBean(it.id, it.title, it.url)
            startActivity<VideoPlayActivity>("item" to videoPlayBean)
        }
    }

    override fun getList(response: ArrayList<VideosBean>?): ArrayList<VideosBean>? {
        return response
    }

    override fun createSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvItemAdapter()
    }

    override fun createSpecialPresenter(): BaseListPresenter {
        return MvItemPresenterImpl(mCode,this)
    }
}