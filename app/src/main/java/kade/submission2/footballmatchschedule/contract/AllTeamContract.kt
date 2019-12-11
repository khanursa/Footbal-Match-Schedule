package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.Team

interface AllTeamContract {
    interface View{
        fun loadAllTeamSuccess()

        fun loadAllTeamFailled(message: String)

        fun progressLoadATeam(load: Boolean)
    }

    interface Presenter{
        fun allTeam(id: Int)
        fun getAllTeam(): ArrayList<Team>
    }
}