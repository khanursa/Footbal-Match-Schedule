package kade.submission2.footballmatchschedule.contract

import kade.submission2.footballmatchschedule.model.DetailTeam

interface DatailTeamContract {
    interface View{
        fun loadDataTeamSuccess()

        fun loadDataTeamFailled(message: String)
    }

    interface Presenter{
        fun dataTeam(id: Int)

        fun getDataTeam(): DetailTeam
    }
}