package kade.submission2.footballmatchschedule.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.AllTeamAdapter
import kade.submission2.footballmatchschedule.adapter.NextPastScheduleAdapter
import kade.submission2.footballmatchschedule.contract.EventSearchContract
import kade.submission2.footballmatchschedule.contract.SearchTeamContract
import kade.submission2.footballmatchschedule.network.Connection
import kade.submission2.footballmatchschedule.presenter.EventSearchPresenter
import kade.submission2.footballmatchschedule.presenter.SearchTeamPresenter
import kade.submission2.footballmatchschedule.view.DetailEventActivity
import kade.submission2.footballmatchschedule.view.DetailTeamActivity
import kotlinx.android.synthetic.main.fragment_pencarian.*
import kotlinx.android.synthetic.main.fragment_pencarian.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.support.v4.startActivity
import kotlin.properties.Delegates

class SearchFragment : Fragment(), EventSearchContract.View, NextPastScheduleAdapter.ClickItem, SearchTeamContract.View, AllTeamAdapter.ClickItem {
    private val tags: String = "SEARCHEVENT"
    private lateinit var presSearchEvent: EventSearchPresenter
    private lateinit var presenterSearchTeam: SearchTeamPresenter
    private lateinit var root: View
    private lateinit var mode: String
    private var adapter by Delegates.notNull<NextPastScheduleAdapter>()
    private var adapterTeam by Delegates.notNull<AllTeamAdapter>()

    override fun onClickItem(id: Int) {
        if(mode.equals("Search Team", true)){
            startActivity<DetailTeamActivity>("idTeam" to id)
        }else
            startActivity<DetailEventActivity>("idEvent" to id)
    }

    override fun onLoadSearchTeamSuccess() {

        root.recyclerPencaraian.visibility = View.VISIBLE
        root.iconSearchView.onActionViewCollapsed()
        root.textLeftToolbar.visibility = View.VISIBLE
        root.switchSearch.visibility = View.VISIBLE
        setingRecyclerTeam()
        root.progressSearch.visibility = View.GONE

    }

    override fun onLoadSearchTeamFailled(message: String) {

        toastMessage("TEAM tidak dapat di temukan\n silahkan cari TEAM lainnya")
        Log.d(tags, message)
        root.progressSearch.visibility = View.GONE
    }

    override fun loadSearchEventSuccess() {
        root.recyclerPencaraian.visibility = View.VISIBLE
        root.iconSearchView.onActionViewCollapsed()
        root.textLeftToolbar.visibility = View.VISIBLE
        root.switchSearch.visibility = View.VISIBLE
        setingRecyclerEvent()
        root.progressSearch.visibility = View.GONE
    }

    override fun loadSearchEventFailled(message: String) {
        toastMessage("EVENT tidak dapat di temukan\n silahkan cari EVENT lainnya")
        Log.d(tags, message)
        root.progressSearch.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_pencarian, container, false)
        root.iconSearchView.visibility = View.VISIBLE
        root.iconSearchView.queryHint = "Type match or team in here!!"
        root.textLeftToolbar.visibility = View.VISIBLE
        root.switchSearch.visibility = View.VISIBLE
        root.textCenterToolbar.visibility = View.GONE

        root.recyclerPencaraian.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        checkConnection()



        root.refreshSearch.setOnRefreshListener {
            checkConnection()
            refreshSearch.isRefreshing = false
        }

        return root
    }

    private fun setingRecyclerEvent() {
        val eventSearch =
            presSearchEvent.getEventSearch().filter { it.strSport == "Soccer" } as ArrayList
        adapter = NextPastScheduleAdapter(eventSearch)
        adapter.setOnClickListener(this)
        root.recyclerPencaraian.adapter = adapter
    }

    private fun setingRecyclerTeam(){
        val teamSearch = presenterSearchTeam.getSearchTeam().filter { it.strSport == "Soccer" } as ArrayList
        adapterTeam = AllTeamAdapter(teamSearch)
        adapterTeam.setOncLickListener(this)
        root.recyclerPencaraian.adapter = adapterTeam

    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun checkConnection() {

        if (Connection().checkConnection(requireContext())) {
            root.noConn.visibility = View.GONE
            root.recyclerPencaraian.visibility = View.VISIBLE
            presSearchEvent = EventSearchPresenter(this)
            presenterSearchTeam = SearchTeamPresenter(this)

            mode = "Search Match"
            root.textLeftToolbar.text = mode
            searchView()

            root.switchSearch.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    mode = "Search Team"
                    root.textLeftToolbar.text = mode

                    searchView()
                } else {
                    mode = "Search Match"
                    root.textLeftToolbar.text = mode

                    searchView()
                }
            }


        } else {
            root.noConn.visibility = View.VISIBLE
            root.recyclerPencaraian.visibility = View.GONE
        }
    }

    private fun searchView(): Boolean {
        root.iconSearchView.setOnSearchClickListener {
            root.textLeftToolbar.visibility = View.GONE
            root.switchSearch.visibility = View.GONE
            root.progressSearch.visibility = View.VISIBLE
            root.recyclerPencaraian.visibility = View.GONE
        }
        root.iconSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchView", "text submit : $query")
                if(mode.equals("Search Team", true)){
                    query?.let {presenterSearchTeam.SearchTeam(it)}
                }else query?.let { presSearchEvent.EventSearch(it) }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchView", "text change : $newText")
                return false
            }
        })
        return true
    }


}