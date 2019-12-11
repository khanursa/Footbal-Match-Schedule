package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Event


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-21
 */

interface EventSearchContract {
    interface View{
        fun loadSearchEventSuccess()

        fun loadSearchEventFailled(message: String)
    }

    interface Presenter{
        fun EventSearch(event: String)

        fun getEventSearch(): ArrayList<Event>
    }
}