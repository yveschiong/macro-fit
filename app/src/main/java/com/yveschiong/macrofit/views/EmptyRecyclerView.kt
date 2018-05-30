package com.yveschiong.macrofit.views

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


class EmptyRecyclerView : RecyclerView {
    @Nullable
    private var emptyView: View? = null
    private val observer= object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            initEmptyView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            initEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            initEmptyView()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private fun initEmptyView() {
        if (emptyView != null) {
            val isEmpty = (adapter == null || adapter.itemCount == 0)
            emptyView!!.visibility = if (isEmpty) View.VISIBLE else View.GONE
            visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        super.setAdapter(adapter)
        oldAdapter?.unregisterAdapterDataObserver(observer)
        adapter?.registerAdapterDataObserver(observer)
    }

    fun setEmptyView(view: View) {
        this.emptyView = view
        initEmptyView()
    }
}