package com.example.cpmark.player.adapter

import android.os.Bundle
import android.support.v4.app.BundleCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.ViewGroup
import com.example.cpmark.player.ui.fragment.MvItemFragment
import com.itheima.player.model.bean.MvAreaBean



/**
 * Created by cpMark on 2017/10/5.
 */
class MvAdapter(val mDatalist:ArrayList<MvAreaBean>,fm:FragmentManager):FragmentPagerAdapter(fm) {

    val TAG = "MvAdapter"

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("code",mDatalist[position].code)
        val fragment = MvItemFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return mDatalist?.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mDatalist[position].name
    }
}