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
import kade.submission2.footballmatchschedule.model.TeamFavorite
import kotlinx.android.synthetic.main.holder_daftar_liga.view.*
import org.jetbrains.anko.db.delete


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */
 
class TeamFavoriteAdapter (private var favoriteTeam: ArrayList<TeamFavorite>): RecyclerView.Adapter<TeamFavoriteAdapter.ViewHolder>() {
    private lateinit var listener: ClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_daftar_liga, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favoriteTeam.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(favoriteTeam[position])

        holder.itemView.teamFavorite.setOnClickListener {
            holder.removeFromFavorite()
            if (holder.remove) {
                removeItem(position)
            }
        }

        holder.itemView.setOnClickListener {
            listener.onClickItem(holder.idTeam)
        }
    }

    private fun removeItem(position: Int) {
        favoriteTeam.removeAt(position)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: ClickItem) {
        this.listener = listener
    }

    interface ClickItem {
        fun onClickItem(id: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var remove: Boolean = false
        var idTeam: Int = 0
        private var idFavorite: Long = 0
        private var mappingImage: MappingImage = MappingImage()
        private val fullLove =
            itemView.context.resources.getDrawable(R.drawable.ic_favorite_black_24dp)

        fun bindData(data: TeamFavorite) {
            data.id?.let {
                idFavorite = it
            }
            data.idTeam.let {
                idTeam = it?.toInt()!!
            }
            itemView.nameTeam.text = "${data.name}"
            itemView.textTeam.text = "${data.descTeam}"
            mappingImage.placingImage(itemView.context, "${data.image}", itemView.posterTeam)
            itemView.teamFavorite.setImageDrawable(fullLove)
        }

        fun removeFromFavorite() {
            try {
                itemView.context?.database?.use {
                    delete(TeamFavorite.TABLE_TEAM_FAVORITE, "ID_ = {id}", "id" to idFavorite)
                }
                remove = true
                toastMessage("success remove favorite")
            } catch (e: SQLiteConstraintException) {
                Log.d("FAVORITE", "remove Favorite : ${e.message}")
                toastMessage("Failled remove favorite from database")
                remove = false
            }
        }

        private fun toastMessage(message: String) {
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }
    }

}

