package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.DetailLeague


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-08
 */

interface DetailLeagueContract {
    interface View{
        fun loadDeatilLeagueSuccess()

        fun loadDetailLeagueFailled(message: String)

        fun progressLoadDLeague(load: Boolean)
    }

    interface Presenter{
        fun detailLeague(id: Int)

        fun getDetailLeague(): DetailLeague?
    }
}