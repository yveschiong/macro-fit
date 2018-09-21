package com.yveschiong.macrofit.presenters

import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.QueryTextEvent
import com.yveschiong.macrofit.contracts.USDASearchViewContract
import com.yveschiong.macrofit.network.search.SearchApi
import com.yveschiong.macrofit.network.search.SearchResult
import com.yveschiong.macrofit.repositories.FoodRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class USDASearchListPresenter<V : USDASearchViewContract.View> @Inject constructor(
    private val bus: EventBus,
    private val foodRepository: FoodRepository,
    private val searchApi: SearchApi
) : RootPresenter<V>(), USDASearchViewContract.Presenter<V> {

    override fun onAttach(view: V) {
        super.onAttach(view)

        bus.listen<QueryTextEvent>()
            .call {
                fetchSearchResults(it.query)
            }
    }

    override fun fetchSearchResults(query: String?) {
        query?.let { q ->
            // Only show search results for queries with length of 2 or longer
            if (q.length <= 1) {
                view?.showSearchResults(ArrayList())
                return@let
            }

            searchApi.getSearchResults(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { view?.showSearchResults(it.list) },
                    { view?.showSearchResults(ArrayList()) }
                )
        }
    }

    override fun onSearchResultClicked(searchResult: SearchResult) {
        // Do nothing for now
    }
}
