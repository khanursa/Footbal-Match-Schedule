package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.TeamContract
import kade.submission2.footballmatchschedule.model.DetailTeam
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-20
 */
 
open class TeamPresenter (val View: TeamContract.View): ApiFactory(), TeamContract.Presenter{
    private var detailTeam: DetailTeam? = null
    override fun detailTeam(id: Int, home: Boolean) {
        View.progressLoad(true)
        disposable = sportdbApi.getDetailTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
//                detailTeam = result.teams as ArrayList
                for(i in result.teams?.indices!!){
                    detailTeam = result.teams[i]
                }

                View.loadTeamSuccess(home)
                View.progressLoad(false)
            },{
                error ->
                error.message?.let {
                    View.loadTeamFailled(it)
                }
            })
    }

    override fun getTeam(): DetailTeam? {
        return detailTeam
    }
}