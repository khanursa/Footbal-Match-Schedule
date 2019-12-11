package kade.submission2.footballmatchschedule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.AllPlayerAdapter
import kade.submission2.footballmatchschedule.contract.AllPlayerContract
import kade.submission2.footballmatchschedule.contract.TeamContract
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.presenter.AllPlayerPresenter
import kade.submission2.footballmatchschedule.presenter.TeamPresenter
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import kotlin.properties.Delegates


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

@SuppressLint("DEPRECATION")
class DetailTeamActivity: AppCompatActivity(), TeamContract.View, AllPlayerContract.View, AllPlayerAdapter.ClickPlayer{
    private var mappingImage = MappingImage()
    private val tags = "DETAILTEAMACTIVITY"
    private var idTeam: Int? = 133619
    private var presenterTeam = TeamPresenter(this)
    private var presenterPlayer = AllPlayerPresenter(this)
    private var adapter by Delegates.notNull<AllPlayerAdapter>()
    private fun toastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun placingTeam(
        nameTeam: String?,
        logo: String?,
        jersey: String?,
        banner: String?,
        league: String?,
        stadium: String?,
        country: String?,
        descrip: String?
    ){
        textCenterToolbar.text = nameTeam

        logo?.let {
            mappingImage.placingImage(this, it, logoTeam)
        }

        jersey?.let {
            mappingImage.placingImage(this, it, imageJersey)
        }

        banner?.let {
            mappingImage.placingImage(this, it, bannerTeam)
        }

        teamInform1.text = "Country : $country\nLeague : $league\nStadium : $stadium"
        teamInform2.text = descrip
    }

    private fun setingRecycler(){
        adapter = AllPlayerAdapter(presenterPlayer.getListPlayer())
        adapter.setOnClickPlayer(this)
        recyclerPlayer.adapter = adapter
    }

    override fun setPlayer(idPlayer: Int) {
        startActivity<DetailPlayerActivity>("idPlayer" to idPlayer)
    }

    override fun onLoadAllPlayerSuccess() {
        setingRecycler()
    }

    override fun onLoadAllPlayerFailled(message: String) {
        Log.d(tags, message)
        toastMessage("Upps can't load LIST PLAYER")
    }

    override fun loadTeamSuccess(home: Boolean) {
        val team = presenterTeam.getTeam()
        placingTeam(
            team?.strAlternate,
            team?.strTeamBadge,
            team?.strTeamJersey,
            team?.strTeamBanner,
            team?.strLeague,
            team?.strStadium,
            team?.strCountry,
            team?.strDescriptionEN
        )
    }

    override fun loadTeamFailled(message: String) {
        Log.d(tags, message)
        toastMessage("Upps can't load TEAM")
    }

    override fun progressLoad(load: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        recyclerPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        textCenterToolbar.visibility = View.VISIBLE

        idTeam = intent?.extras?.getInt("idTeam")
        presenterTeam
        presenterPlayer

        idTeam?.let {
            presenterTeam.detailTeam(it, false)
            presenterPlayer.allPlayer(it)
        }
    }
}