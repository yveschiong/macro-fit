package com.yveschiong.macrofit.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.adapters.recyclerview.USDASearchListAdapter
import com.yveschiong.macrofit.contracts.USDASearchViewContract
import com.yveschiong.macrofit.extensions.afterMeasured
import com.yveschiong.macrofit.interfaces.OnAdapterViewClicked
import com.yveschiong.macrofit.network.search.SearchResult
import kotlinx.android.synthetic.main.fragment_food.view.*
import javax.inject.Inject

class USDASearchFragment : BaseFragment(), USDASearchViewContract.View {

    @Inject
    lateinit var presenter: USDASearchViewContract.Presenter<USDASearchViewContract.View>

    private val adapter: USDASearchListAdapter = USDASearchListAdapter(ArrayList(), object : OnAdapterViewClicked.SearchResultView {
        override fun onViewClicked(searchResult: SearchResult) {
            presenter.onSearchResultClicked(searchResult)
        }
    })

    companion object {
        fun newInstance(): USDASearchFragment {
            return USDASearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_usda_search, container, false)

        view.afterMeasured { view.recyclerView.setEmptyView(emptyView) }
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.isNestedScrollingEnabled = false
        view.recyclerView.adapter = adapter

        App.graph.inject(this)

        presenter.onAttach(this)

        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun showSearchResults(searchResultsList: List<SearchResult>) {
        adapter.setData(searchResultsList)
        adapter.notifyDataSetChanged()
    }

    override fun showSearchResultDetailActivity(searchResult: SearchResult) {

    }
}