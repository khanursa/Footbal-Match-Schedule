package kade.submission2.footballmatchschedule.contract

import androidx.annotation.Nullable
import kade.submission2.footballmatchschedule.model.Event


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-16
 */

interface EventContract {
    interface View {

        fun loadNextPastSuccess()

        fun loadNextEventsFailled(message: String)

        fun loadPastEventsFailled(message: String)

        fun progressLoadEvent(load: Boolean)
    }

    interface Presenter {

        fun eventSoccer(@Nullable id: Int)

        fun getNextEvent(): ArrayList<Event>

        fun getPastEvent(): ArrayList<Event>

//        fun searchEvent(event: String)
//        fun getSearchEvent(): ArrayList<Event>
//
//        fun nextEvents(id: Int)
//        fun getNextEvents(): ArrayList<Event>
//
//        fun pastEvents(id: Int)
//        fun getPastEvent(): ArrayList<Event>
//
//        fun lookevents(id: Int)
//        fun getLookEvent(): ArrayList<Event>

    }
}