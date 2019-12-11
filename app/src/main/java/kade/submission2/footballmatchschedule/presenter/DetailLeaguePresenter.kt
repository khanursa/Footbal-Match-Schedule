package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.DetailLeagueContract
import kade.submission2.footballmatchschedule.model.DetailLeague
import kade.submission2.footballmatchschedule.service.ApiFactory

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-08
 */

data class DetailLeaguePresenter(val view: DetailLeagueContract.View): ApiFactory(), DetailLeagueContract.Presenter{
    private var detailLeague: DetailLeague? = null
    override fun detailLeague(id: Int) {
        view.progressLoadDLeague(true)
        disposable = sportdbApi.getDetailLeague(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                for(i in result.leagues?.indices!!){
                    detailLeague = result.leagues[i]
                }

                view.loadDeatilLeagueSuccess()
                view.progressLoadDLeague(false)
            },{
                error ->
                error.message?.let {
                    view.loadDetailLeagueFailled(it)
                    view.progressLoadDLeague(false)
                }
            })
    }

    override fun getDetailLeague(): DetailLeague? {
        return detailLeague
    }

}