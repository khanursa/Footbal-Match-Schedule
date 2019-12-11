package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.EventContract
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-19
 */

open class EventPresenter(val view: EventContract.View) : ApiFactory(), EventContract.Presenter {
    private var nextEvent: ArrayList<Event> = ArrayList()
    private var pastEvent: ArrayList<Event> = ArrayList()
    var load: Boolean = false

    private fun nextEvent(id: Int) {
        disposable = sportdbApi.getNextEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                nextEvent = result.events as ArrayList
                load = true
            }, { error ->
                error.message?.let {
                    view.loadNextEventsFailled(it)
                }
            })
    }

    private fun pastEvent(id: Int) {
        disposable = sportdbApi.getPastEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                pastEvent = result.events as ArrayList
                if(load) view.loadNextPastSuccess()
                view.progressLoadEvent(false)
            }, { error ->
                error.message?.let {
                    view.loadPastEventsFailled(it)
                }
                view.progressLoadEvent(false)
            })
    }

    override fun eventSoccer(id: Int) {
        view.progressLoadEvent(true)
        nextEvent(id)
        pastEvent(id)
    }

    override fun getNextEvent(): ArrayList<Event> {
        return nextEvent
    }

    override fun getPastEvent(): ArrayList<Event> {
        return pastEvent
    }
}