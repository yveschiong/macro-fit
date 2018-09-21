package com.yveschiong.macrofit.network.search

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    fun getSearchResults(
        @Query("q") query: String = "",

        // Maximum rows to return
        @Query("max") max: Int = 50,

        // Beginning row in the result set to begin
        @Query("offset") offset: Int = 0,

        // Sort the results by food name (n) or by search relevance (r)
        @Query("sort") sort: String = "r",

        // Must be either 'Branded Food Products' or 'Standard Reference'
        @Query("ds") dataSource: String = "Standard Reference",

        @Query("fg") foodGroupId: String = ""
    ): Observable<List<SearchResult>>
}