package com.wang.myNote.base

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author wanggh8
 * @description ViewHolder基类
 * @date 2020/8/25 2:19 PM
 */
abstract class BaseViewHolder<T>(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    abstract fun onBind(t: T, position: Int)
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return itemView.findViewById<View>(id) as T
    }
}