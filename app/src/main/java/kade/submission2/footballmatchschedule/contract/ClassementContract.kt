package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Classement


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-29
 */

interface ClassementContract {
    interface View{
        fun onLoadClassementLeagueSucces()

        fun onLoadClassementLeagueFailled(message: String)
    }
    interface Presenter{
        fun classementLeague(idLeague: Int)

        fun getClassement(): ArrayList<Classement>
    }
}