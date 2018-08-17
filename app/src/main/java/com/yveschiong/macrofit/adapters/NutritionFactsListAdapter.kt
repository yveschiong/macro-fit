package com.yveschiong.macrofit.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.presenters.NutritionFactsListPresenter
import com.yveschiong.macrofit.viewholders.NutritionFactsListViewHolder

class NutritionFactsListAdapter(var presenter: NutritionFactsListPresenter?) : RecyclerView.Adapter<NutritionFactsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionFactsListViewHolder {
        return NutritionFactsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_nutrition_fact, parent, false))
    }

    override fun onBindViewHolder(holder: NutritionFactsListViewHolder, position: Int) {
        presenter?.populate(holder, position)
    }

    override fun getItemCount(): Int {
        return presenter?.getItemCount() ?: 0
    }
}
