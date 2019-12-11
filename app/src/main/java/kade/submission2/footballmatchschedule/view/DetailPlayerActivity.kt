package kade.submission2.footballmatchschedule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.contract.DetailPlayerContract
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.presenter.DetailPlayerPresenter
import kade.submission2.footballmatchschedule.view.fragment.FanartFragment
import kotlinx.android.synthetic.main.activitty_detail_player.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerContract.View {
    private var presenterPlayer = DetailPlayerPresenter(this)
    private var mappingImage = MappingImage()
    private var idPlayer: Int? = 0
    private val tags = "DETAILPLAYER"
    private var currentPage: Int = -1
    private var fanart1: String? = ""
    private var fanart2: String? = ""
    private var fanart3: String? = ""
    private var fanart4: String? = ""
    @SuppressLint("SetTextI18n")
    private fun placingPlayer(
        cutOut: String?,
        playerName: String?,
        born: String?,
        team: String?,
        height: String,
        weight: String,
        position: String?,
        birthLoc: String?,
        national: String?,
        description: String?

    ) {
        textCenterToolbar.text = "Detail Player"
        cutOut?.let {
            mappingImage.placingImage(this, it, imageCutoutPlayer)
        }
        informPlayer1.text =
            "Name : $playerName\nNational : $national\nTeam : $team\nPosition : $position\nHeight : $height\nWeight : $weight\nBorn : $born\nBirth Location : $birthLoc"

        informPlayer2.text = description
    }
    private fun pagerBanner() {
        indicatorFanart.count = 4
        viewFanart.adapter = PagerCorouselAdapter(supportFragmentManager)

        val handler = Handler()
        val update = Runnable {
            if (currentPage > 4) {
                currentPage = 0
            }
            indicatorFanart.selection = currentPage++
            viewFanart.setCurrentItem(indicatorFanart.selection, true)
            currentPage = currentPage++
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)

        viewFanart.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                indicatorFanart.selection = position
                if (currentPage != position) currentPage = position
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    override fun onLoadPlayerSuccess() {
        presenterPlayer.getPlayer()?.let {
            fanart1 = it.strFanart1
            fanart2 = it.strFanart2
            fanart3 = it.strFanart3
            fanart4 = it.strFanart4
            placingPlayer(
                it.strCutout,
                it.strPlayer,
                it.dateBorn,
                it.strTeam,
                it.strHeight,
                it.strWeight,
                it.strPosition,
                it.strBirthLocation,
                it.strNationality,
                it.strDescriptionEN
                )
        }
        if (fanart1 != null || fanart2 != null || fanart3 != null || fanart4 != null) {
            layoutFanartPlayer.visibility = View.VISIBLE
            pagerBanner()
        } else {
            layoutFanartPlayer.visibility = View.GONE
        }
    }

    override fun onLoadPlayerFailled(message: String) {
Toast.makeText(this, "Upps... can't load PLAYER", Toast.LENGTH_SHORT).show()
        Log.d(tags, message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitty_detail_player)
        idPlayer = intent?.extras?.getInt("idPlayer")
        idPlayer?.let {
            presenterPlayer.detailPlayer(it)
        }
    }

    inner class PagerCorouselAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FanartFragment().newInstance(fanart1)
                1 -> FanartFragment().newInstance(fanart2)
                2 -> FanartFragment().newInstance(fanart3)
                3 -> FanartFragment().newInstance(fanart4)
                else -> FanartFragment().newInstance(fanart1)
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }
}