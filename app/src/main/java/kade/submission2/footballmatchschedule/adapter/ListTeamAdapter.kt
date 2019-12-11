package kade.submission2.footballmatchschedule.adapter

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
import kotlinx.android.synthetic.main.holder_list_team.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-06
 */

class ListTeamAdapter(
    private var allTeam: ArrayList<Team>
) :
    RecyclerView.Adapter<ListTeamAdapter.ListViewHolder>() {
    private lateinit var listener: ClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_list_team, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allTeam.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindItemData(allTeam[position], listener)
    }

    fun replaceData(allLeague: List<Team>) {
        this.allTeam.addAll(allLeague)
        notifyItemRangeChanged(0, allLeague.size)
    }

    fun setOncLickListener(listener: ClickItem) {
        this.listener = listener
    }

    interface ClickItem {
        fun onClickItem(id: Int)
    }

    class ListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var mappingImage: MappingImage = MappingImage()
        private var image: String = ""
        private var name: String = ""
        private var desc: String = ""
        private var id: String = ""
        private var isFavorite = false
        private val fullLove = itemView.context.resources.getDrawable(R.drawable.ic_favorite_black_24dp)
        private val love = itemView.context.resources.getDrawable(R.drawable.ic_favorite_border_black_24dp)

        fun bindItemData(allTeam: Team, listener: ClickItem) {
            id = "${allTeam.idTeam}"
            name = allTeam.strTeam
            desc = "Stadium : ${allTeam.strStadium}\nDescription\n${allTeam.strDescriptionEN}"
            itemView.imageTeamName.text = name
            allTeam.strTeamBadge?.let {
                image = it
                mappingImage.placingImage(itemView.context, it, itemView.imageTeam)
            }


            itemView.setOnClickListener {
                listener.onClickItem(allTeam.idTeam)
            }

            favoriteState()

            if(isFavorite){
                itemView.teamFavorite.setImageDrawable(fullLove)
            }else
                itemView.teamFavorite.setImageDrawable(love)
            isFavorite = false

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