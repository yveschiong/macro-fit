package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.recyclerview.NutritionFactsListAdapter
import com.yveschiong.macrofit.contracts.NutritionFactsViewContract
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.NutritionFact
import kotlinx.android.synthetic.main.fragment_food.view.*
import javax.inject.Inject

class NutritionFactsFragment: BaseFragment(), NutritionFactsViewContract.View {

    @Inject
    lateinit var presenter: NutritionFactsViewContract.Presenter<NutritionFactsViewContract.View>

    private var adapter: NutritionFactsListAdapter = NutritionFactsListAdapter(ArrayList())

    companion object {
        fun newInstance(): NutritionFactsFragment {
            return NutritionFactsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nutrition_facts, container, false)

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.isNestedScrollingEnabled = false
        view.recyclerView.adapter = adapter

        App.graph.inject(this)

        presenter.onAttach(this)
        presenter.fetchNutrition()

        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun showNutrition(nutritionList: List<NutritionFact>) {
        adapter.setData(nutritionList)
        adapter.notifyDataSetChanged()
    }
}