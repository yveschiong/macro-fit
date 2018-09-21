package com.yveschiong.macrofit.adapters.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.interfaces.OnAdapterViewClicked
import com.yveschiong.macrofit.network.search.SearchResult
import com.yveschiong.macrofit.viewholders.USDASearchListViewHolder

class USDASearchListAdapter(
    private var searchResultList: List<SearchResult>,
    private val listener: OnAdapterViewClicked.SearchResultView
) : RecyclerView.Adapter<USDASearchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): USDASearchListViewHolder {
        val holder = USDASearchListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false))

        // Handle the click for the card
        holder.itemView.setOnClickListener {
            listener.onViewClicked(searchResultList[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: USDASearchListViewHolder, position: Int) {
        with(searchResultList[position]) {
            holder.foodNameTextView.text = name
        }
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    fun setData(searchResultList: List<SearchResult>) {
        this.searchResultList = searchResultList
    }
}