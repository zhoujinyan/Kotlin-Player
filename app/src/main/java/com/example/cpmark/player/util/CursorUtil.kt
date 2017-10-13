package com.example.cpmark.player.util

import android.database.Cursor
import android.util.Log


/**
 * ClassName:CursorUtil
 * Description:cursor打印util
 */
object CursorUtil {
    val TAG = "CursorUtil"

    fun logCursor(cursor: Cursor?){
        cursor?.let {
            //将cursor游标复位
            it.moveToPosition(-1)
            while (it.moveToNext()){
                for (index in 0 until it.columnCount){
                    Log.e(TAG,"key=${it.getColumnName(index)} --value=${it.getString(index)}")
                }
            }
        }
    }
}