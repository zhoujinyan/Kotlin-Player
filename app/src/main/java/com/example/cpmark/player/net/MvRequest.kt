package com.example.cpmark.player.net

import com.example.cpmark.player.util.URLProviderUtils
import com.itheima.player.model.bean.HomeItemBean
import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by cpMark on 2017/10/4.
 */
class MvRequest(type: Int, resultHanlder: ResultHanlder<ArrayList<MvAreaBean>>) :
        MRequest<ArrayList<MvAreaBean>>(type, URLProviderUtils.getMVareaUrl(), resultHanlder) {
}