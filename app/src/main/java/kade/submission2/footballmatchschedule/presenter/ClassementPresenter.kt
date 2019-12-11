package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.ClassementContract
import kade.submission2.footballmatchschedule.model.Classement
import kade.submission2.footballmatchschedule.service.ApiFactory

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-29
 */

open class ClassementPresenter(val view: ClassementContract.View): ApiFactory(), ClassementContract.Presenter{
    private var getClassement: ArrayList<Classement> = ArrayList()

    override fun classementLeague(idLeague: Int) {
        disposable = sportdbApi.getClassement(idLeague)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                getClassement = result.table as ArrayList
                view.onLoadClassementLeagueSucces()
            },{
                error ->
                error.message?.let {
                    view.onLoadClassementLeagueFailled(it)
                }
            })
    }

    override fun getClassement(): ArrayList<Classement> {
            return getClassement
    }
}