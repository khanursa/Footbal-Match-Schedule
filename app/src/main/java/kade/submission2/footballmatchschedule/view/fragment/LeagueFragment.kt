package kade.submission2.footballmatchschedule.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
import androidx.fragment.app.Fragment
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.AllTeamAdapter
import kade.submission2.footballmatchschedule.contract.AllLeagueContract
import kade.submission2.footballmatchschedule.contract.AllTeamContract
import kade.submission2.footballmatchschedule.contract.ClassementContract
import kade.submission2.footballmatchschedule.network.Connection
import kade.submission2.footballmatchschedule.presenter.AllTeamPresenter
import kade.submission2.footballmatchschedule.presenter.ClassementPresenter
import kade.submission2.footballmatchschedule.presenter.LeaguePresenter
import kade.submission2.footballmatchschedule.view.DetailLeagueActivity
import kotlinx.android.synthetic.main.fragment_liga.*
import kotlinx.android.synthetic.main.fragment_liga.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.find

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-06
 */

class LeagueFragment : Fragment(), AllLeagueContract.View, AllTeamContract.View,
    ClassementContract.View,
    AllTeamAdapter.ClickItem {
    private val tags: String = "LeagueFragment"
    private lateinit var presenterTeam: AllTeamPresenter
    private lateinit var presenterLeague: LeaguePresenter
    private lateinit var presenterClassement: ClassementPresenter
    private lateinit var root: View
    private var idLeague: Int = 0
    private var tableSize: Int = 1
    private var spinnerItem: ArrayList<String> = ArrayList()

    override fun onClickItem(id: Int) {
        // dont do it
    }

    override fun onLoadClassementLeagueSucces() {
        root.tableLayout.removeAllViews()
        val headerRow = layoutInflater.inflate(R.layout.holder_header_table_classement, null)
        root.tableLayout.addView(headerRow)
        for (i in presenterClassement.getClassement().indices) {
            setTable(
                presenterClassement.getClassement()[i].name,
                "${presenterClassement.getClassement()[i].played}",
                "${presenterClassement.getClassement()[i].win}",
                "${presenterClassement.getClassement()[i].draw}",
                "${presenterClassement.getClassement()[i].loss}"
            )
            tableSize++
        }
    }

    override fun onLoadClassementLeagueFailled(message: String) {
        Log.d(tags, message)
        messageUser("classement can't be load")
    }

    override fun progressLoad(load: Boolean) {
        if (load) {
            root.progressLiga.visibility = View.VISIBLE
        } else root.progressLiga.visibility = View.GONE
    }

    override fun loadListLeagueSuccess() {
        for (i in presenterLeague.getAllLeague().indices) {
            if (presenterLeague.getAllLeague()[i].strSport.equals("Soccer", true))
                spinnerItem.add(presenterLeague.getAllLeague()[i].strLeague)
        }
        settingSpinner()
    }

    override fun loadListLeagueFailled(message: String) {
        Log.d(tags, message)
        messageUser("list league can't be load")
    }

    @SuppressLint("SetTextI18n")
    override fun loadAllTeamSuccess() {
        root.detailLeague.visibility = View.VISIBLE
        root.detailLeague.text = "Detail ${presenterTeam.getAllTeam()[0].strLeague}"
        root.detailLeague.visibility = View.VISIBLE


    }

    override fun loadAllTeamFailled(message: String) {
        Log.d(tags, message)
        root.detailLeague.visibility = View.GONE
        messageUser("list Team is empety")
    }

    override fun progressLoadATeam(load: Boolean) {

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_liga, container, false)
        root.textCenterToolbar.text = "The Best List of Football"
        presenterTeam = AllTeamPresenter(this)
        presenterLeague = LeaguePresenter(this)
        presenterClassement = ClassementPresenter(this)


        checkConnection()

        root.refreshLiga.setOnRefreshListener {
            checkConnection()
            refreshLiga.isRefreshing = false
        }

        root.detailLeague.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("idLeague", idLeague)
            val intent = Intent(requireContext(), DetailLeagueActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        return root
    }

    private fun settingSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerItem
        )
        root.spiner.adapter = spinnerAdapter
        root.spiner.setSelection(0)
        spinnerAction()

        // set default recyclerVIew
        idLeague = presenterLeague.getAllLeague()[0].idLeague
        presenterTeam.allTeam(idLeague)
        presenterClassement.classementLeague(idLeague)
    }

    private fun spinnerAction() {
        root.spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                for (i in presenterLeague.getAllLeague().indices) {
                    if (presenterLeague.getAllLeague()[i].strLeague.equals(
                            root.spiner.selectedItem.toString(),
                            true
                        )
                    ) idLeague = presenterLeague.getAllLeague()[i].idLeague
                }
                Log.d(tags, root.spiner.selectedItem.toString())
                presenterTeam.allTeam(idLeague)
                presenterClassement.classementLeague(idLeague)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setTable(name: String, played: String, win: String, draw: String, loss: String) {
        val row = layoutInflater.inflate(R.layout.holder_table_classment, null)
        row.find<TextView>(R.id.nameTeam).text = name
        row.find<TextView>(R.id.playedTeam).text = played
        row.find<TextView>(R.id.winTeam).text = win
        row.find<TextView>(R.id.drawTeam).text = draw
        row.find<TextView>(R.id.lossTeam).text = loss

        root.tableLayout.addView(row)
    }

    private fun messageUser(message: CharSequence) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun checkConnection() {
        root.progressLiga.visibility = View.VISIBLE
        if (Connection().checkConnection(requireContext())) {
            root.noConn.visibility = View.GONE
            root.tableLayout.visibility = View.VISIBLE
            root.progressLiga.visibility = View.GONE
            presenterLeague.allLeague()
        } else {
            root.noConn.visibility = View.VISIBLE
            root.tableLayout.visibility = View.GONE
            root.progressLiga.visibility = View.GONE
        }
    }
}