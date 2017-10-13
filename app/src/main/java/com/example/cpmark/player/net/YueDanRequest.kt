package com.example.cpmark.player.net

import com.example.cpmark.player.util.URLProviderUtils
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by cpMark on 2017/10/4.
 */
class YueDanRequest(type: Int, offset: Int, resultHanlder: ResultHanlder<YueDanBean>) :
        MRequest<YueDanBean>(type, URLProviderUtils.getYueDanUrl(offset, 10), resultHanlder) {


}