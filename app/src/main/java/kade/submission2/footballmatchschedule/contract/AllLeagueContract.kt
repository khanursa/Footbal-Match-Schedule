package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.AllLeague

interface AllLeagueContract{
    interface View: BaseContract.View{

        fun loadListLeagueSuccess()

        fun loadListLeagueFailled(message: String)
    }

    interface Presenter{
        fun allLeague()

        fun getAllLeague(): ArrayList<AllLeague>
    }
}