package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.DetailTeam


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-20
 */

interface TeamContract {
    interface View: BaseContract.View{
        fun loadTeamSuccess(home: Boolean)

        fun loadTeamFailled(message: String)
    }

    interface Presenter{
        fun detailTeam(id: Int, home: Boolean)

        fun getTeam(): DetailTeam?
    }
}