package kade.submission2.footballmatchschedule.adapter

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.helper.database
import kade.submission2.footballmatchschedule.model.Team
import kade.submission2.footballmatchschedule.model.TeamFavorite
import kotlinx.android.synthetic.main.holder_daftar_liga.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-06
 */

class AllTeamAdapter(
    private var allTeam: ArrayList<Team>
) :
    RecyclerView.Adapter<AllTeamAdapter.ListLeagueAdapterViewHolder>() {
    private lateinit var listener: ClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLeagueAdapterViewHolder {
        return ListLeagueAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_daftar_liga, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allTeam.size
    }

    override fun onBindViewHolder(holder: ListLeagueAdapterViewHolder, position: Int) {
        holder.bindItemData(allTeam[position], listener)
    }

    fun setOncLickListener(listener: ClickItem) {
        this.listener = listener
    }

    interface ClickItem {
        fun onClickItem(id: Int)
    }

    class ListLeagueAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var mappingImage: MappingImage = MappingImage()
        private var image: String = ""
        private var name: String = ""
        private var id: String = ""
        private var desc: String = ""
        private var isFavorite = false
        private val fullLove = itemView.context.resources.getDrawable(R.drawable.ic_favorite_black_24dp)
        private val love = itemView.context.resources.getDrawable(R.drawable.ic_favorite_border_black_24dp)

        @SuppressLint("SetTextI18n")
        fun bindItemData(allTeam: Team, listener: ClickItem) {
            id = "${allTeam.idTeam}"
            name = allTeam.strTeam
            desc = "Stadium : ${allTeam.strStadium}\nDescription\n${allTeam.strDescriptionEN}"
            itemView.nameTeam.text = name
            itemView.textTeam.text = desc
            allTeam.strTeamBadge.let {
                image = it
                mappingImage.placingImage(itemView.context, it, itemView.posterTeam)
            }

            favoriteState()

            if(isFavorite){
                itemView.teamFavorite.setImageDrawable(fullLove)
            }else
                itemView.teamFavorite.setImageDrawable(love)
            isFavorite = false

            itemView.setOnClickListener {
                listener.onClickItem(allTeam.idTeam)
            }

            itemView.teamFavorite.setOnClickListener {
                Log.d("FAVORITE", "fullLove : $fullLove\nlove : $love")
                if (itemView.teamFavorite.drawable == fullLove) {
                    removeFromFavorite()
                    itemView.teamFavorite.setImageDrawable(love)
                } else {
                    addFavorite()
                    itemView.teamFavorite.setImageDrawable(fullLove)
                }
            }
        }

        private fun addFavorite() {
            try {
                itemView.context.database.use {
                    insert(
                        TeamFavorite.TABLE_TEAM_FAVORITE,
                        TeamFavorite.ID_TEAM to id,
                        TeamFavorite.IMAGE_TEAM to image,
                        TeamFavorite.NAME_TEAM to name,
                        TeamFavorite.DESC_TEAM to desc
                    )
                }
                toastMessage("success add Favorite")
            } catch (e: SQLiteConstraintException) {
                Log.d("FAVORITE", "add Favorite : ${e.message}")
                toastMessage("Failled add Favorite to database")
            }
        }

        private fun favoriteState() {
            itemView.context.database.use {
                val result = select(TeamFavorite.TABLE_TEAM_FAVORITE)
                    .whereArgs(
                        "(ID_TEAM = {id})",
                        "id" to id
                    )
                val favorite = result.parseList(classParser<TeamFavorite>())
                if (favorite.isNotEmpty()) isFavorite = true
            }
        }

        private fun removeFromFavorite() {
            try {
                itemView.context.database.use {
                    delete(TeamFavorite.TABLE_TEAM_FAVORITE, "ID_TEAM = {teamId}", "teamId" to id)
                }
                toastMessage("success remove favorite")
            } catch (e: SQLiteConstraintException) {
                Log.d("FAVORITE", "remove Favorite : ${e.message}")
                toastMessage("Failled remove favorite from database")
            }
        }

        private fun toastMessage(message: String){
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }
    }
}