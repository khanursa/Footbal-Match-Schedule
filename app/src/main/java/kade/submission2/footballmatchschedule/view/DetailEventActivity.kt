package kade.submission2.footballmatchschedule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.contract.DetailEventContract
import kade.submission2.footballmatchschedule.contract.TeamContract
import kade.submission2.footballmatchschedule.presenter.DetailEventPresenter
import kade.submission2.footballmatchschedule.presenter.TeamPresenter
import kotlinx.android.synthetic.main.activity_detail_event.*

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-22
 */

class DetailEventActivity : AppCompatActivity(), DetailEventContract.View, TeamContract.View {
    private val tag: String = "DETAILEVENTACTIVITY"
    private lateinit var presenterEvent: DetailEventPresenter
    private lateinit var presenterTeam: TeamPresenter

    override fun progressLoad(load: Boolean) {
    }

    override fun loadTeamSuccess(home: Boolean) {
        if (home) {
            presenterTeam.getTeam()?.strTeamBadge?.let {
                inflateImage(it, logoTeamHome)
            }
        } else presenterTeam.getTeam()?.strTeamBadge?.let {
            inflateImage(it, logoTeamAway)
        }
    }

    override fun loadTeamFailled(message: String) {
        Log.d(tag, message)
    }

    override fun loadDetailEventSuccess() {
//        presenterEvent.getDetailEvent()?.idHomeTeam?.toInt()
//            ?.let { presenterTeam.detailTeam(it, true) }
//
//        presenterEvent.getDetailEvent()?.idHomeTeam?.toInt()
//            ?.let { presenterTeam.detailTeam(it, false) }

        presenterTeam.detailTeam(presenterEvent.getDetailEvent().idHomeTeam.toInt(), true)
        presenterTeam.detailTeam(presenterEvent.getDetailEvent().idAwayTeam.toInt(), false)

        setUpHomeTeam(
            presenterEvent.getDetailEvent().strHomeTeam,
            presenterEvent.getDetailEvent().intHomeScore,
            presenterEvent.getDetailEvent().strHomeGoalDetails,
            presenterEvent.getDetailEvent().intHomeShots,
            presenterEvent.getDetailEvent().strHomeYellowCards,
            presenterEvent.getDetailEvent().strHomeRedCards,
            presenterEvent.getDetailEvent().strHomeFormation,
            presenterEvent.getDetailEvent().strHomeLineupGoalkeeper,
            presenterEvent.getDetailEvent().strHomeLineupDefense,
            presenterEvent.getDetailEvent().strHomeLineupMidfield,
            presenterEvent.getDetailEvent().strHomeLineupForward,
            presenterEvent.getDetailEvent().strHomeLineupSubstitutes
        )

        setUpAwayTeam(
            presenterEvent.getDetailEvent().strAwayTeam,
            presenterEvent.getDetailEvent().intAwayScore,
            presenterEvent.getDetailEvent().strAwayGoalDetails,
            presenterEvent.getDetailEvent().intAwayShots,
            presenterEvent.getDetailEvent().strAwayYellowCards,
            presenterEvent.getDetailEvent().strAwayRedCards,
            presenterEvent.getDetailEvent().strAwayFormation,
            presenterEvent.getDetailEvent().strAwayLineupGoalkeeper,
            presenterEvent.getDetailEvent().strAwayLineupDefense,
            presenterEvent.getDetailEvent().strAwayLineupMidfield,
            presenterEvent.getDetailEvent().strAwayLineupForward,
            presenterEvent.getDetailEvent().strAwayLineupSubstitutes
        )
    }

    override fun loadDetailEventFailled(message: String) {
        toastMessage(message)
        Log.d(tag, message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        presenterEvent = DetailEventPresenter(this)
        presenterTeam = TeamPresenter(this)

        val idEvent = intent?.extras?.getInt("idEvent")
        idEvent?.let {
            presenterEvent.detailEvent(it)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUpHomeTeam(
        home: String,
        skor: Int?,
        goal: String?,
        shoot: Int?,
        yellowCard: String?,
        redCard: String?,
        formation: String?,
        gkeeper: String?,
        defense: String?,
        midfield: String?,
        forward: String?,
        sub: String?
    ) {
        homeTeam.text = "HOME\n$home"
        skorHome.text = "$skor"
        goalHome.text = goal
        shootHome.text = "$shoot"
        yellowCardHome.text = "$yellowCard"
        redCardHome.text = "$redCard"
        formationHome.text = formation
        lineupHome.text = "GoalKeeper : $gkeeper\n\n" +
                "Defense : $defense\n\n" +
                "Midfield : $midfield\n\n" +
                "Forward : $forward\n\n\n" +
                "Substitutes : $sub"
    }

    @SuppressLint("SetTextI18n")
    fun setUpAwayTeam(
        away: String,
        skor: Int?,
        goal: String?,
        shoot: Int?,
        yellowCard: String?,
        redCard: String?,
        formation: String?,
        gkeeper: String?,
        defense: String?,
        midfield: String?,
        forward: String?,
        sub: String?
    ) {
        awayTeam.text = "AWAY\n$away"
        skorAway.text = "$skor"
        goalAway.text = goal
        shootAway.text = "$shoot"
        yellowCardAway.text = yellowCard
        redCardAway.text = redCard
        formationAway.text = formation
        lineupAway.text = "GoalKeeper : $gkeeper\n\n" +
                "Defense : $defense\n\n" +
                "Midfield : $midfield\n\n" +
                "Forward : $forward\n\n\n" +
                "Substitutes : $sub"
    }

    private fun inflateImage(url: String, image: ImageView) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_broken_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
            .into(image)
    }

    private fun toastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}