package com.yveschiong.macrofit.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.yveschiong.macrofit.R

class USDASearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val foodNameTextView: TextView

    init {
        foodNameTextView = itemView.findViewById(R.id.name)
    }
}
