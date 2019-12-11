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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.ListTeamAdapter
import kade.submission2.footballmatchschedule.adapter.ScheduleAdapter
import kade.submission2.footballmatchschedule.contract.AllTeamContract
import kade.submission2.footballmatchschedule.contract.DetailLeagueContract
import kade.submission2.footballmatchschedule.contract.EventContract
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.model.DetailLeague
import kade.submission2.footballmatchschedule.network.Connection
import kade.submission2.footballmatchschedule.presenter.AllTeamPresenter
import kade.submission2.footballmatchschedule.presenter.DetailLeaguePresenter
import kade.submission2.footballmatchschedule.presenter.EventPresenter
import kade.submission2.footballmatchschedule.view.fragment.FanartFragment
import kotlinx.android.synthetic.main.activity_detail_league.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.properties.Delegates

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-08
 */

@Suppress("DEPRECATION")
class DetailLeagueActivity : AppCompatActivity(), DetailLeagueContract.View, AllTeamContract.View,
    EventContract.View,
    ListTeamAdapter.ClickItem {

    private val tags: String = "DetailLeagueActivity"
    private lateinit var presenterDetailLeague: DetailLeaguePresenter
    private lateinit var presenterTeam: AllTeamPresenter
    private lateinit var presenterEvent: EventPresenter
    private var mappingImage: MappingImage = MappingImage()
    private var detailLeague: DetailLeague? = null
    private var lTeamAdapter by Delegates.notNull<ListTeamAdapter>()
    private var scheduleAdapter by Delegates.notNull<ScheduleAdapter>()
    private var idLeague: Int? = 4328 // default liga inggris
    private var currentPage: Int = -1
    private var fanart1: String? = ""
    private var fanart2: String? = ""
    private var fanart3: String? = ""
    private var fanart4: String? = ""

    override fun onClickItem(id: Int) {
        startActivity<DetailTeamActivity>("idTeam" to id)
    }

    override fun loadNextPastSuccess() {
        matchSchedule()
    }

    override fun loadNextEventsFailled(message: String) {
        toast("Next Event -> $message")
        Log.d(tags, message)
    }

    override fun loadPastEventsFailled(message: String) {
        toast("Past Event -> $message")
        Log.d(tags, message)
    }

    override fun progressLoadEvent(load: Boolean) {
        if (load) {
            progressSchedule.visibility = View.VISIBLE
            sectionMatchSchedule.visibility = View.GONE
        } else {
            progressSchedule.visibility = View.GONE
            sectionMatchSchedule.visibility = View.VISIBLE
        }
    }

    override fun loadAllTeamSuccess() {
        lTeamAdapter.replaceData(presenterTeam.getAllTeam())
    }

    override fun loadAllTeamFailled(message: String) {
        toast("Team -> $message")
        Log.d(tags, message)
    }

    override fun progressLoadATeam(load: Boolean) {
        if (load) {
            progressBanner.visibility = View.VISIBLE
            sectionTeam.visibility = View.GONE
        } else {
            progressBanner.visibility = View.GONE
            sectionTeam.visibility = View.VISIBLE
        }
    }

    override fun loadDeatilLeagueSuccess() {
        detailLeague = presenterDetailLeague.getDetailLeague()
        fanart1 = detailLeague?.strFanart1.toString()
        fanart2 = detailLeague?.strFanart2.toString()
        fanart3 = detailLeague?.strFanart3.toString()
        fanart4 = detailLeague?.strFanart4.toString()
        sectionBody(
            detailLeague?.strLeague.toString(),
            detailLeague?.strBadge.toString(),
            detailLeague?.strDescriptionEN.toString(),
            detailLeague?.strLeagueAlternate.toString(),
            detailLeague?.strCountry.toString(),
            detailLeague?.strDivision.toString(),
            detailLeague?.strFacebook.toString(),
            detailLeague?.strTwitter.toString(),
            detailLeague?.strWebsite.toString(),
            detailLeague?.strTrophy.toString()
        )

        if (fanart1 != null || fanart2 != null || fanart3 != null || fanart4 != null) {
            sectionBanner.visibility = View.VISIBLE
            pagerBanner()
        } else {
            sectionBanner.visibility = View.GONE
        }

    }

    override fun loadDetailLeagueFailled(message: String) {
        toast("League -> $message")
        Log.d(tags, message)
    }

    override fun progressLoadDLeague(load: Boolean) {
        if (load) {
            progressDetail.visibility = View.VISIBLE
            sectionBody.visibility = View.GONE
            indicator.visibility = View.GONE
        } else {
            progressDetail.visibility = View.GONE
            sectionBody.visibility = View.VISIBLE
            indicator.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sectionBody(
        name: String,
        logo: String,
        desc: String?,
        nameAlt: String,
        country: String,
        divisi: String,
        facebook: String,
        twiter: String,
        website: String,
        trophy: String
    ) {
        infoLeague1.text = "Facebook : $facebook \nTwiter : $twiter \nWebsite : $website"
        nameLeague.text = name
        mappingImage.placingImage(this, logo, logoLeague)
        mappingImage.placingImage(this, trophy, imageTrophy)
        infoLeague2.text =
            "Division : $divisi \nName Alternate : $nameAlt \nCountry : $country\n\n$desc"
    }

    private fun settingRecycler() {
        lTeamAdapter = ListTeamAdapter(presenterTeam.getAllTeam())
        lTeamAdapter.setOncLickListener(this)
        recyclerTeam.adapter = lTeamAdapter
    }

    private fun pagerBanner() {
        indicator.count = 4
        viewBanner.adapter = PagerCorouselAdapter(supportFragmentManager)

        val handler = Handler()
        val update = Runnable {
            if (currentPage > 4) {
                currentPage = 0
            }
            indicator.selection = currentPage++
            viewBanner.setCurrentItem(indicator.selection, true)
            currentPage = currentPage++
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)

        viewBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                indicator.selection = position
                if (currentPage != position) currentPage = position
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun toast(message: String) {
        Toast.makeText(this@DetailLeagueActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkConnection() {
        if (Connection().checkConnection(this@DetailLeagueActivity)) {
            noConn.visibility = View.GONE
            idLeague?.let {
                presenterDetailLeague.detailLeague(it)
                presenterTeam.allTeam(it)
                presenterEvent.eventSoccer(it)
            }
            settingRecycler()
        } else {
            noConn.visibility = View.VISIBLE
        }
    }

    private fun matchSchedule() {
        scheduleAdapter = ScheduleAdapter(
            this,
            supportFragmentManager,
            2,
            presenterEvent.getNextEvent(),
            presenterEvent.getPastEvent()
        )
        frameSchedule.adapter = scheduleAdapter
        tabsSchedule.setupWithViewPager(frameSchedule)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        recyclerTeam.layoutManager =
            LinearLayoutManager(this@DetailLeagueActivity, RecyclerView.HORIZONTAL, false)

        presenterDetailLeague = DetailLeaguePresenter(this)
        presenterTeam = AllTeamPresenter(this)
        presenterEvent = EventPresenter(this)
        idLeague = intent?.extras?.getInt("idLeague")

        checkConnection()

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