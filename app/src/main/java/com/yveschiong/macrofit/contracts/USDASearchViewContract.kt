package com.yveschiong.macrofit.contracts

import com.yveschiong.macrofit.interfaces.BasePresenter
import com.yveschiong.macrofit.interfaces.BaseView
import com.yveschiong.macrofit.network.search.SearchResult

interface USDASearchViewContract {
    interface View : BaseView {
        fun showSearchResults(searchResultsList: List<SearchResult>)
        fun showSearchResultDetailActivity(searchResult: SearchResult)
    }

    interface Presenter<V: View> : BasePresenter<V> {
        fun fetchSearchResults()
        fun onSearchResultClicked(searchResult: SearchResult)
    }
}