package com.example.cpmark.player.net

import com.example.cpmark.player.util.URLProviderUtils
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by cpMark on 2017/10/4.
 */
class HomeRequest(type: Int, offset: Int, resultHanlder: ResultHanlder<ArrayList<HomeItemBean>>) :
        MRequest<ArrayList<HomeItemBean>>(type,URLProviderUtils.getHomeUrl(offset, 10), resultHanlder) {


}