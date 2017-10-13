package com.example.cpmark.player.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.example.cpmark.player.R
import com.example.cpmark.player.adapter.MvAdapter
import com.example.cpmark.player.base.BaseFragment
import com.example.cpmark.player.presenter.impl.MvPresenterImpl
import com.example.cpmark.player.view.MvView
import com.itheima.player.model.bean.MvAreaBean
import kotlinx.android.synthetic.main.fragment_mv.*
import android.view.ViewGroup



/**
 * Created by cpMark on 2017/9/30.
 */
class MVFragment : BaseFragment(),MvView {

    val mPresenter by lazy {
        MvPresenterImpl(this)
    }

    override fun initFragmentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_mv,null)
    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun onError(message: String?) {
        mainThreadToast("加载失败$message")
    }

    override fun loadSuccess(data: ArrayList<MvAreaBean>?) {
        data?.let {
            vp.adapter = MvAdapter(data,childFragmentManager)
            tl.setupWithViewPager(vp)
        }
    }
}