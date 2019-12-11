package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.DetailEventContract
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.service.ApiFactory

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-21
 */
 
open class DetailEventPresenter (val view: DetailEventContract.View): ApiFactory(), DetailEventContract.Presenter{
    lateinit var event: Event

    override fun detailEvent(id: Int) {
        disposable = sportdbApi.getDetailEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                for(i in result.events.indices){
                    event = result.events[i]
                }
                view.loadDetailEventSuccess()
            },{
                error ->
                error.message?.let {
                    view.loadDetailEventFailled(it)
                }
            })
    }

    override fun getDetailEvent(): Event {
        return event
    }
}