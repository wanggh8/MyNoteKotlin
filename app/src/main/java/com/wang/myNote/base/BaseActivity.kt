package com.wang.myNote.base

import android.app.ActivityManager
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.wang.myNote.BootApplication
import java.util.*

/**
 * @author wanggh8
 * @description activity基类
 * @date 2020/8/25 2:10 PM
 */
abstract class BaseActivity : FragmentActivity(), View.OnClickListener {
    /**
     * 设置布局文件
     */
    abstract val contentLayout: Int

    /**
     * 在实例化布局前处理的逻辑
     */
    abstract fun beforeInitView()

    /**
     * 实例化布局文件/组件
     */
    abstract fun initView()

    /**
     * 在实例化布局后处理的逻辑
     */
    abstract fun afterInitView()

    /**
     * 绑定监听事件
     */
    abstract fun bindListener()

    /**
     * 点击事件
     */
    abstract fun onClickEvent(v: View?)
    var lastClickTime: Long = 0
    var isKeyBack = false
    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > 300) {
            lastClickTime = currentTime
            onClickEvent(v)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(contentLayout)
        BootApplication.instance.addActivity(this)
        // 获取当前创建的Activity
        Log.d("onCreate Activity ：", this.javaClass.simpleName)
        beforeInitView()
        initView()
        afterInitView()
        bindListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isKeyBack = true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        Handler().postDelayed({
            if (!isForeground && !isKeyBack) {
                //ToastUtil.show(APPUtil.getAppName(BootApplication.getAppContext()) + "已进入后台");
            }
            isKeyBack = false
        }, 200)
        super.onPause()
    }

    //当前应用是否处于前台
    private val isForeground: Boolean
        private get() {
            val am = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val cn = am.getRunningTasks(1)[0].topActivity
            val currentPackageName = cn!!.packageName
            return if (!TextUtils.isEmpty(currentPackageName) && currentPackageName == this.packageName) {
                true
            } else false
        }
}