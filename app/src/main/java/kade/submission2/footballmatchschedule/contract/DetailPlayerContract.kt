package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Player


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

interface DetailPlayerContract {
    interface View{
        fun onLoadPlayerSuccess()

        fun onLoadPlayerFailled(message: String)
    }

    interface Presenter{
        fun detailPlayer(idPlayer: Int)

        fun getPlayer(): Player?
    }
}