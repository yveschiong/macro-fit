package com.yveschiong.macrofit.search

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.yveschiong.macrofit.UnitTests
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.QueryTextEvent
import com.yveschiong.macrofit.contracts.USDASearchViewContract
import com.yveschiong.macrofit.network.search.SearchApi
import com.yveschiong.macrofit.network.search.SearchResult
import com.yveschiong.macrofit.network.search.SearchResultList
import com.yveschiong.macrofit.presenters.USDASearchListPresenter
import com.yveschiong.macrofit.repositories.FoodRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock


class USDASearchTests : UnitTests() {
    @Mock
    private lateinit var view: USDASearchViewContract.View

    @Mock
    private lateinit var foodRepository: FoodRepository

    @Mock
    private lateinit var searchApi: SearchApi

    private lateinit var bus: EventBus

    private lateinit var presenter: USDASearchListPresenter<USDASearchViewContract.View>

    @Before
    fun setup() {
        bus = EventBus(PublishSubject.create())
        presenter = USDASearchListPresenter(bus, foodRepository, searchApi)
        presenter.onAttach(view)
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }

    @Test
    fun search_withNullQuery_doesNothing() {
        val query = null

        presenter.fetchSearchResults(query)

        verify(view, never()).showSearchResults(ArgumentMatchers.anyList())
    }

    @Test
    fun search_withEmptyQuery_returnsEmptyList() {
        val query = ""

        presenter.fetchSearchResults(query)

        verify(view).showSearchResults(ArgumentMatchers.anyList())
    }

    @Test
    fun search_withQueryOfLength1_returnsEmptyList() {
        val query = "a"

        presenter.fetchSearchResults(query)

        verify(view).showSearchResults(ArgumentMatchers.anyList())
    }

    @Test
    fun search_withQueryOfNormalLength_returnsSearchResultList() {
        val query = "brown rice"
        val searchResultList = SearchResultList(arrayListOf(SearchResult()))

        doReturn(Observable.just(searchResultList))
            .`when`(searchApi)
            .getSearchResults(query)

        presenter.fetchSearchResults(query)

        verify(view).showSearchResults(searchResultList.list)
    }

    @Test
    fun search_withQueryOfNormalLength_returnsNoSearchResultList() {
        val query = "brown rice"

        doReturn(Observable.error<SearchResultList>(Exception("Couldn't find any results")))
            .`when`(searchApi)
            .getSearchResults(query)

        presenter.fetchSearchResults(query)

        verify(view).showSearchResults(ArgumentMatchers.anyList())
    }

    @Test
    fun search_withQueryEvent_returnsSearchResultList() {
        val query = "brown rice"
        val queryTextEvent = QueryTextEvent(query)
        val searchResultList = SearchResultList(arrayListOf(SearchResult()))

        doReturn(Observable.just(searchResultList))
            .`when`(searchApi)
            .getSearchResults(query)

        bus.post(queryTextEvent)

        verify(view).showSearchResults(searchResultList.list)
    }
}
