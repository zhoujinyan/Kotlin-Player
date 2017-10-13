package com.example.cpmark.player.util

import com.example.cpmark.player.R
import com.example.cpmark.player.base.BaseFragment
import com.example.cpmark.player.ui.fragment.HomeFragment
import com.example.cpmark.player.ui.fragment.MVFragment
import com.example.cpmark.player.ui.fragment.VBangFragment
import com.example.cpmark.player.ui.fragment.YueDanFragment


/**
 * ClassName:FragmentUtil
 * Description:管理fragment的util类
 */
class FragmentUtil private constructor(){//私有化构造方法
    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { MVFragment() }
    val vbangFragment by lazy { VBangFragment() }
    val yuedanFragment by lazy { YueDanFragment() }
    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }

    /**
     * 根据tabid获取对应的fragment
     */
    fun getFragment(tabId:Int): BaseFragment?{
        when(tabId){
            R.id.tab_home->return homeFragment
            R.id.tab_mv->return mvFragment
            R.id.tab_vbang->return vbangFragment
            R.id.tab_yuedan->return yuedanFragment
        }
        return null
    }
}