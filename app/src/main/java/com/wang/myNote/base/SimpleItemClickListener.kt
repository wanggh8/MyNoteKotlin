package com.wang.myNote.base

import android.view.View

/**
 * @author wanggh8
 * @description 点击时间基类
 * @date 2020/8/25 2:19 PM
 */
interface SimpleItemClickListener<T> {
    fun onItemClickListener(v: View?, bean: T, position: Int)
}