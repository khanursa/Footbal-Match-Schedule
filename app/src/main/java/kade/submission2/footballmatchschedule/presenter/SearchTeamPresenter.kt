package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.SearchTeamContract
import kade.submission2.footballmatchschedule.model.Team
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */
 
open class SearchTeamPresenter (val view: SearchTeamContract.View): ApiFactory(), SearchTeamContract.Presenter{
    private var listView: ArrayList<Team> = ArrayList()

    override fun SearchTeam(team: String) {
        disposable = sportdbApi.getSearchTeam(team)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                listView = result.teams as ArrayList
                view.onLoadSearchTeamSuccess()
            },{
                error ->
                error.message?.let {
                    view.onLoadSearchTeamFailled(it)
                }
            })
    }

    override fun getSearchTeam(): ArrayList<Team> {
        return listView
    }
}