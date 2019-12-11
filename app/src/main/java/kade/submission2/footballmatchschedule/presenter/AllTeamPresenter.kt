package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.AllTeamContract
import kade.submission2.footballmatchschedule.model.Team
import kade.submission2.footballmatchschedule.service.ApiFactory

open class AllTeamPresenter(var view: AllTeamContract.View) : ApiFactory(), AllTeamContract.Presenter {
    private var allTeam: ArrayList<Team> = ArrayList()
    override fun allTeam(id: Int) {
        view.progressLoadATeam(true)
        disposable = sportdbApi.getALLTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                allTeam = result.teams as ArrayList
                view.loadAllTeamSuccess()
                view.progressLoadATeam(false)
            }, { error ->
                error.message?.let {
                    view.loadAllTeamFailled(it)
                    view.progressLoadATeam(false)
                }
            })
    }

    override fun getAllTeam(): ArrayList<Team> {
        return allTeam
    }
}