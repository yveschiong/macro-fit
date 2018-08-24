package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.FoodListAdapter
import com.yveschiong.macrofit.contracts.FoodViewContract
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.Food
import kotlinx.android.synthetic.main.fragment_food.view.*
import javax.inject.Inject

class FoodFragment: BaseFragment(), FoodViewContract.View {

    @Inject
    lateinit var presenter: FoodViewContract.Presenter<FoodViewContract.View>

    private var adapter: FoodListAdapter = FoodListAdapter(ArrayList())

    companion object {
        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.isNestedScrollingEnabled = false
        view.recyclerView.adapter = adapter

        App.graph.inject(this)

        presenter.onAttach(this)
        presenter.fetchFood()

        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun showFood(foodList: List<Food>) {
        adapter.setData(foodList)
        adapter.notifyDataSetChanged()
    }
}