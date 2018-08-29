package com.yveschiong.macrofit.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.activities.EditNutritionFactActivity
import com.yveschiong.macrofit.adapters.recyclerview.NutritionFactsListAdapter
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.contracts.NutritionFactsViewContract
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.interfaces.OnAdapterViewClicked
import com.yveschiong.macrofit.models.NutritionFact
import kotlinx.android.synthetic.main.fragment_food.view.*
import javax.inject.Inject

class NutritionFactsFragment: BaseFragment(), NutritionFactsViewContract.View {

    @Inject
    lateinit var presenter: NutritionFactsViewContract.Presenter<NutritionFactsViewContract.View>

    private val adapter: NutritionFactsListAdapter = NutritionFactsListAdapter(ArrayList(), object: OnAdapterViewClicked.NutritionFactView {
        override fun onViewClicked(nutritionFact: NutritionFact) {
            presenter.onCardClicked(nutritionFact)
        }
    })

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

    override fun showEditNutritionFactActivity(nutritionFact: NutritionFact) {
        val intent = Intent(context, EditNutritionFactActivity::class.java)
        intent.putExtra(Constants.EXTRA_NUTRITION_FACT, nutritionFact)
        activity?.startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_NUTRITION_FACT)
    }
}