package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.DatailTeamContract
import kade.submission2.footballmatchschedule.model.DetailTeam
import kade.submission2.footballmatchschedule.service.ApiFactory

open class DataLeaguePresenter (val view: DatailTeamContract.View): ApiFactory(), DatailTeamContract.Presenter{
    private lateinit var dataTeam: DetailTeam

    override fun dataTeam(id: Int) {
        disposable = sportdbApi.getDetailTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    result ->
                dataTeam = result.teams as DetailTeam
                view.loadDataTeamSuccess()
            },{
                    error ->
                error.message?.let {
                    view.loadDataTeamFailled(it)
                }
            })
    }

    override fun getDataTeam(): DetailTeam {
        return dataTeam
    }
}