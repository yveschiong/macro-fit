package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.NutritionFactsListAdapter
import com.yveschiong.macrofit.contracts.NutritionViewContract
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.models.NutritionFact
import kotlinx.android.synthetic.main.fragment_food.view.*
import javax.inject.Inject

class NutritionFactsFragment: BaseFragment(), NutritionViewContract.View {

    @Inject
    lateinit var presenter: NutritionViewContract.Presenter<NutritionViewContract.View>

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

        presenter.fetchNutrition()

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.end()
    }

    override fun showNutrition(nutritionList: List<NutritionFact>) {
        adapter.setData(nutritionList)
        adapter.notifyDataSetChanged()
    }
}