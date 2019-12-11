package kade.submission2.footballmatchschedule.service

import io.reactivex.disposables.Disposable
import kade.submission2.footballmatchschedule.BuildConfig
import kade.submission2.footballmatchschedule.service.client.SportdbApi

open class ApiFactory{

    val sportdbApi : SportdbApi = RetrofitFactory.retrofit(
        BuildConfig.BASE_URL
    )
        .create(SportdbApi::class.java)

    var disposable: Disposable? = null
}