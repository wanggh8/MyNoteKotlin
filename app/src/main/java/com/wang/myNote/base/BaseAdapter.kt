package com.wang.myNote.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.daimajia.swipe.interfaces.SwipeAdapterInterface
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface
import com.daimajia.swipe.util.Attributes
import java.util.*

/**
 * @author wanggh8
 * @description adapter基类
 * @date 2020/8/25 2:10 PM
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T?>>, SwipeItemMangerInterface, SwipeAdapterInterface {
    var mItemManger = SwipeItemRecyclerMangerImpl(this)
    protected var context: Context
    protected var list: MutableList<T>?
    protected var onItemClickListener: ItemClickListener<T?>? = null
    protected var onItemLongClickListener: ItemLongClickListener<T?>? = null
    fun setSimpleOnItemClickListener(itemClickListener: ItemClickListener<T>?) {
        onItemClickListener = itemClickListener
    }

    fun setSimpleOnItemLongClickListener(itemLongClickListener: ItemLongClickListener<T>?) {
        onItemLongClickListener = itemLongClickListener
    }

    interface ItemClickListener<T> {
        fun onItemClick(position: Int, bean: T)
    }

    interface ItemLongClickListener<T> {
        fun onItemLongClick(view: View?, position: Int, bean: T)
    }

    constructor(context: Context, list: MutableList<T>?) {
        this.context = context
        this.list = list
    }

    constructor(context: Context) {
        this.context = context
        list = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T?> {
        val layoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return onCreateViewHolder(layoutInflater, parent, viewType)
    }

    abstract fun onCreateViewHolder(inflater: LayoutInflater?, parent: ViewGroup?, viewType: Int): BaseViewHolder<T?>
    override fun onBindViewHolder(holder: BaseViewHolder<T?>, position: Int) {
        holder.onBind(getItem(position), position)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener { onItemClickListener.onItemClick(position, getItem(position)) }
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener {
                onItemLongClickListener.onItemLongClick(holder.itemView, position, getItem(position))
                true
            }
        }
    }

    fun getItem(position: Int): T? {
        return if (list == null) null else list.get(position)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list.size
    }

    var collection: List<T>?
        get() = list
        set(list) {
            var list = list
            if (list == null) list = ArrayList()
            this.list!!.clear()
            this.list!!.addAll(list)
            notifyDataSetChanged()
        }

    fun appendCollection(l: List<T>?) {
        if (list == null) list = ArrayList()
        list.addAll(l!!)
        notifyDataSetChanged()
    }

    fun insertCollection(pos: Int, l: List<T>?) {
        if (list == null) list = ArrayList()
        list.addAll(pos, l!!)
        notifyDataSetChanged()
    }

    override fun openItem(position: Int) {
        mItemManger.openItem(position)
    }

    override fun closeItem(position: Int) {
        mItemManger.closeItem(position)
    }

    override fun closeAllExcept(layout: SwipeLayout) {
        mItemManger.closeAllExcept(layout)
    }

    override fun closeAllItems() {
        mItemManger.closeAllItems()
    }

    override fun getOpenItems(): List<Int> {
        return mItemManger.openItems
    }

    override fun getOpenLayouts(): List<SwipeLayout> {
        return mItemManger.openLayouts
    }

    override fun removeShownLayouts(layout: SwipeLayout) {
        mItemManger.removeShownLayouts(layout)
    }

    override fun isOpen(position: Int): Boolean {
        return mItemManger.isOpen(position)
    }

    override fun getMode(): Attributes.Mode {
        return mItemManger.mode
    }

    override fun setMode(mode: Attributes.Mode) {
        mItemManger.mode = mode
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return position
    }
}