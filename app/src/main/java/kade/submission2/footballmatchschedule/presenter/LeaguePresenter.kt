package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.AllLeagueContract
import kade.submission2.footballmatchschedule.model.AllLeague
import kade.submission2.footballmatchschedule.service.ApiFactory

open class LeaguePresenter (var view: AllLeagueContract.View):ApiFactory(),AllLeagueContract.Presenter{
    private var allLeague: ArrayList<AllLeague> = ArrayList()

    override fun allLeague() {
        view.progressLoad(true)
        disposable = sportdbApi.getAllLeague()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                allLeague = result.leagues as ArrayList
                view.loadListLeagueSuccess()
                view.progressLoad(false)
            },{
                error ->
                error.message?.let {
                    view.loadListLeagueFailled(it)
                    view.progressLoad(false)
                }
            })
    }

    override fun getAllLeague(): ArrayList<AllLeague> {
        return allLeague
    }

}