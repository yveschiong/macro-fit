package com.yveschiong.macrofit.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.presenters.FoodListPresenter
import com.yveschiong.macrofit.viewholders.FoodListViewHolder

class FoodListAdapter(private val presenter: FoodListPresenter) : RecyclerView.Adapter<FoodListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        return FoodListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_food, parent, false))
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        presenter.populate(holder, position)
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }
}
