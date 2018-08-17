package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.NutritionFactsListAdapter
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.presenters.NutritionFactsListPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_food.view.*

class NutritionFactsFragment: Fragment() {

    private var disposable = CompositeDisposable()

    private var adapter: NutritionFactsListAdapter? = null

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

        disposable.add(App.graph.nutritionFactsRepository.getNutritionFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter = NutritionFactsListAdapter(NutritionFactsListPresenter(it))
                view.recyclerView.adapter = adapter
                adapter?.notifyDataSetChanged()
            })

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        // Unregister from event bus
        disposable.clear()
    }
}