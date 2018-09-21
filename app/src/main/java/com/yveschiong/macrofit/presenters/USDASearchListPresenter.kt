package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.contracts.USDASearchViewContract
import com.yveschiong.macrofit.network.search.SearchApi
import com.yveschiong.macrofit.network.search.SearchResult
import com.yveschiong.macrofit.repositories.FoodRepository
import javax.inject.Inject

class USDASearchListPresenter<V : USDASearchViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val foodRepository: FoodRepository,
    private val searchApi: SearchApi
) : RootPresenter<V>(), USDASearchViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)
        // Do nothing for now
    }

    override fun fetchSearchResults() {
        // Make a default fetch for now
        searchApi.getSearchResults("brown rice")
            .call { view?.showSearchResults(it.list) }
    }

    override fun onSearchResultClicked(searchResult: SearchResult) {
        // Do nothing for now
    }
}
