package com.wang.myNote.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.util.*

/**
 * @author wanggh8
 * @description fragment基类
 * @date 2020/8/25 2:19 PM
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
    /**
     * 获取布局的layout
     */
    abstract val contentLayout: Int

    /*在实例化布局之前处理的逻辑*/
    abstract fun beforeInitView()

    /*实例化布局文件/组件*/
    abstract fun initView()

    /*在实例化之后处理的逻辑*/
    abstract fun afterInitView()

    /*绑定监听事件*/
    abstract fun bindListener()

    /**
     * 触发点击事件
     */
    abstract fun onClickEvent(v: View?)
    protected var mRootView: View? = null
    var mContext: Activity? = null
    var lastClickTime: Long = 0
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity //mCtx 是成员变量，上下文引用
    }

    override fun getContext(): Context? {
        return mContext
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(contentLayout, container, false)

            // 方便调试，添加当前Fragment名
            Log.d("onCreate mFragment ：", this.javaClass.simpleName)
            beforeInitView()
            initView()
            afterInitView()
            bindListener()
        }
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > 300) {
            lastClickTime = currentTime
            onClickEvent(v)
        }
    }

    protected fun <T : View?> findViewById(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }
}