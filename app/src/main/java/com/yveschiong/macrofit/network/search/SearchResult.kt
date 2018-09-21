package com.yveschiong.macrofit.network.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult (
    @SerializedName("ndbno")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("manu")
    var manufacturer: String = ""
) : Parcelable