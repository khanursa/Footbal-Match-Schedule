package kade.submission2.footballmatchschedule.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.FavoriteAdapter
import kade.submission2.footballmatchschedule.adapter.TeamFavoriteAdapter
import kade.submission2.footballmatchschedule.helper.database
import kade.submission2.footballmatchschedule.model.FavoriteMatch
import kade.submission2.footballmatchschedule.model.TeamFavorite
import kade.submission2.footballmatchschedule.view.DetailEventActivity
import kade.submission2.footballmatchschedule.view.DetailTeamActivity
import kotlinx.android.synthetic.main.fragment_favorite_pertandingan.*
import kotlinx.android.synthetic.main.fragment_favorite_pertandingan.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity
import kotlin.properties.Delegates


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-25
 */

class MatchFavoriteFragment : Fragment(), FavoriteAdapter.ClickItem, TeamFavoriteAdapter.ClickItem {
    private lateinit var root: View
    private var adapter by Delegates.notNull<FavoriteAdapter>()
    private var adapterTeam by Delegates.notNull<TeamFavoriteAdapter>()
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private var favoriteTeams: MutableList<TeamFavorite> = mutableListOf()
    private lateinit var mode : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_favorite_pertandingan, container, false)
        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.textCenterToolbar.text = "My Favorite"
        root.recyclerFavorite.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        mode = "MATCH"
        matchFavorite()
        textFavorite.text = "Event Match Favorite"

        switchFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                teamFavorite()
                textFavorite.text = "Team Favorite"
                mode = "TEAM"
            } else {
                matchFavorite()
                textFavorite.text = "Event Match Favorite"
                mode = "MATCH"
            }
        }

    }

    private fun matchFavorite() {
        showFavorite()
        adapter = FavoriteAdapter(favoriteMatches as ArrayList<FavoriteMatch>)
        adapter.setOnClickListener(this)
        root.recyclerFavorite.adapter = adapter
    }

    private fun teamFavorite() {
        showFavorite()
        adapterTeam = TeamFavoriteAdapter(favoriteTeams as ArrayList<TeamFavorite>)
        adapterTeam.setOnClickListener(this)
        root.recyclerFavorite.adapter = adapterTeam
    }


    private fun showFavorite() {
            if (mode.equals("MATCH", true)) {
                context?.database?.use {
                    favoriteMatches.clear()
                    val result = select(FavoriteMatch.TABLE_FAVORITE)
                    val favorite = result.parseList(classParser<FavoriteMatch>())
                    favoriteMatches.addAll(favorite)
                }

            } else {
                context?.database?.use {
                favoriteTeams.clear()
                val result = select(TeamFavorite.TABLE_TEAM_FAVORITE)
                val favorite = result.parseList(classParser<TeamFavorite>())
                favoriteTeams.addAll(favorite)
            }
        }
    }


    override fun onClickItem(id: Int) {
        if (mode.equals("MATCH", true)) {
            startActivity<DetailEventActivity>("idEvent" to id)
        } else {
            startActivity<DetailTeamActivity>("idTeam" to id)
        }
    }
}