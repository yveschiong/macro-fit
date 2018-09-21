package com.yveschiong.macrofit.injection

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.yveschiong.macrofit.constants.Constants
import com.yveschiong.macrofit.network.ApiInterceptor
import com.yveschiong.macrofit.network.search.SearchApi
import com.yveschiong.macrofit.network.search.SearchDeserializer
import com.yveschiong.macrofit.network.search.SearchResult
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    @Reusable
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Reusable
    fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .registerTypeAdapter(object : TypeToken<List<SearchResult>>() {}.type, SearchDeserializer()).create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(OkHttpClient.Builder().addInterceptor(ApiInterceptor()).build())
            .build()
    }
}