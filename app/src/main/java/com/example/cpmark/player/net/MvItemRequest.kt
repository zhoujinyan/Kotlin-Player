package com.example.cpmark.player.net

import com.example.cpmark.player.util.URLProviderUtils
import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean

/**
 * Created by cpMark on 2017/10/5.
 */
class MvItemRequest(type: Int, code: String, offset: Int, resultHanlder: ResultHanlder<MvPagerBean>) :
        MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code, offset, 10), resultHanlder) {
    }