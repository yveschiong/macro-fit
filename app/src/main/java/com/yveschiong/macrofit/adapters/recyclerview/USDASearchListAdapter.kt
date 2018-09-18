package com.yveschiong.macrofit.adapters.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.viewholders.USDASearchListViewHolder

class USDASearchListAdapter(

) : RecyclerView.Adapter<USDASearchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): USDASearchListViewHolder {
        val holder = USDASearchListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false))

        return holder
    }

    override fun onBindViewHolder(holder: USDASearchListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}