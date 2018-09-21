package com.yveschiong.macrofit.network.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// Just a wrapper for a list of search results
@Parcelize
data class SearchResultList (
    @SerializedName("item")
    var list: List<SearchResult>
) : Parcelable