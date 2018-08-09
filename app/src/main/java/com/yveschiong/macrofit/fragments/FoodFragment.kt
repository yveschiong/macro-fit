package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.extensions.afterMeasured
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_food.view.*

class FoodFragment: Fragment() {

    companion object {
        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        view.afterMeasured { recyclerView.setEmptyView(emptyView) }
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}