package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Player

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

interface AllPlayerContract {
    interface View{
        fun onLoadAllPlayerSuccess()

        fun onLoadAllPlayerFailled(message: String)
    }

    interface Presenter{
        fun allPlayer(idTeam: Int)

        fun getListPlayer(): ArrayList<Player>
    }
}