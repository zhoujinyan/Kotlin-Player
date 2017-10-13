package com.example.cpmark.player.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import com.example.cpmark.player.R
import com.example.cpmark.player.adapter.VBangAdapter
import com.example.cpmark.player.base.BaseFragment
import com.example.cpmark.player.ui.activity.AudioPlayActivity
import com.itheima.player.model.AudioBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

/**
 * Created by cpMark on 2017/9/30.
 */
class VBangFragment : BaseFragment() {

    private val REQUEST_PERMISSION = 100

    override fun initFragmentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_vbang, null)
    }

    private var mAdapter: VBangAdapter? = null

    override fun initData() {
        handlePermission()
    }

    override fun initListener() {
        mLv.setOnItemClickListener { _, _, i, _ ->
            mAdapter?.let {
                val cursor = it.getItem(i) as Cursor
                val dataList = AudioBean.getAudioBeans(cursor)
                startActivity<AudioPlayActivity>("list" to dataList, "position" to i)
            }
        }
    }

    private fun handlePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            loadData()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //需要弹出对话框
                alert("我们只是访问音乐资源，不会访问隐私文件", "温馨提示") {
                    yesButton { myRequestPermissions() }
                    noButton {}
                }.show()
            } else {
                //不需要弹出
                myRequestPermissions()
            }
        }
    }

    private fun myRequestPermissions() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permissions, REQUEST_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadData()
        }
    }

    private fun loadData() {
        Observable.create<Cursor> {
            val cursor = context.contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST),
                    null, null, null)
            it.onNext(cursor)
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val cursor = it as Cursor
                    context?.let {
                        mAdapter = VBangAdapter(context, cursor)
                        mLv.adapter = mAdapter
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        //关闭cursor
        mAdapter?.changeCursor(null)
    }

}