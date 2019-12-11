package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Event


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-21
 */

interface DetailEventContract {
    interface View{
        fun loadDetailEventSuccess()

        fun loadDetailEventFailled(message: String)
    }

    interface Presenter{
        fun detailEvent(id: Int)

        fun getDetailEvent(): Event?
    }
}