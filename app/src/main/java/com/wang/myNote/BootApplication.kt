package com.wang.myNote

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Process

/**
 * @author wanggh8
 * @description Application
 * @date 2020/8/25 2:19 PM
 */
class BootApplication : Application() {

    // 使用伴生单例，实现伪静态
    companion object {
        var mApplication: BootApplication? = null
        // 活动管理
        private var mActivityLists: MutableList<Activity>? = null
        // 全局Context
        private var mAppContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mActivityLists = mutableListOf()
        mApplication = this
        mAppContext = applicationContext
    }

    /**
     * 添加activity
     *
     * @param activity 当前活动
     */
    fun addActivity(activity: Activity) {
        if (mActivityLists != null) {
            //不存在则添加
            if (!mActivityLists!!.contains(activity)) {
                mActivityLists!!.add(activity)
            }
        }
    }

    /**
     * 移除所有活动
     */
    fun removeAllActivity() {
        if (mActivityLists != null) {
            for (activity in mActivityLists!!) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
        }
    }

    /**
     * 移除当前活动外所有活动
     *
     * @param self 当前活动
     */
    fun removeOthersActivity(self: Activity) {
        if (mActivityLists != null) {
            for (activity in mActivityLists!!) {
                if (activity !== self || !activity.isFinishing) {
                    activity.finish()
                }
            }
        }
    }

    /**
     * 退出APP
     */
    fun onEixt() {
        removeAllActivity()
        Handler().postDelayed({ Process.killProcess(Process.myPid()) }, 300)
    }

}