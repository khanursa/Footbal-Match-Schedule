package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.AllPlayerContract
import kade.submission2.footballmatchschedule.model.Player
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */
 
open class AllPlayerPresenter (val view: AllPlayerContract.View): ApiFactory(), AllPlayerContract.Presenter{
    private var listPlayer: ArrayList<Player> = ArrayList()

    override fun allPlayer(idTeam: Int) {
        disposable = sportdbApi.getListPlayer(idTeam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                listPlayer = result.player as ArrayList
                view.onLoadAllPlayerSuccess()
            },{
                error ->
                error.message?.let {
                    view.onLoadAllPlayerFailled(it)
                }
            })
    }

    override fun getListPlayer(): ArrayList<Player> {
        return listPlayer
    }
}