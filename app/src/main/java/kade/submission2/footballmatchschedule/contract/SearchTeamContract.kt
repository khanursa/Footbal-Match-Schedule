package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Team


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

interface SearchTeamContract {
    interface View{
        fun onLoadSearchTeamSuccess()

        fun onLoadSearchTeamFailled(message: String)
    }

    interface Presenter{
        fun SearchTeam(team: String)

        fun getSearchTeam(): ArrayList<Team>
    }
}