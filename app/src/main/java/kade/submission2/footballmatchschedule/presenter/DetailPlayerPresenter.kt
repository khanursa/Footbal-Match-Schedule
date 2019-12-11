package kade.submission2.footballmatchschedule.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kade.submission2.footballmatchschedule.contract.DetailPlayerContract
import kade.submission2.footballmatchschedule.model.Player
import kade.submission2.footballmatchschedule.service.ApiFactory


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */
 
open class DetailPlayerPresenter (val view: DetailPlayerContract.View): ApiFactory(), DetailPlayerContract.Presenter{
    private var player: Player? = null

    override fun detailPlayer(idPlayer: Int) {
        disposable = sportdbApi.getDetailPlayer(idPlayer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                for(i in result.players.indices){
                    player = result.players[i]
                }
                view.onLoadPlayerSuccess()
            },{
                error ->
                error.message?.let {
                    view.onLoadPlayerFailled(it)
                }
            })
    }

    override fun getPlayer(): Player? {
        return player
    }
}