package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.EventSearchContract
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-21
 */

class EventSearchPresenter(val view: EventSearchContract.View): ApiFactory(), EventSearchContract.Presenter{
    private var eventSearch: ArrayList<Event> = ArrayList()

    override fun EventSearch(event: String) {
        disposable = sportdbApi.getSearchEvent(event)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                eventSearch = result.event as ArrayList
                view.loadSearchEventSuccess()
            },{
                error ->
                error.message?.let {
                    view.loadSearchEventFailled(it)
                }
            })
    }

    override fun getEventSearch(): ArrayList<Event> {
        return  eventSearch
    }
}