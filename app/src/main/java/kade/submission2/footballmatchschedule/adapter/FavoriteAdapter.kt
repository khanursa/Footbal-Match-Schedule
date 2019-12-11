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
import kade.submission2.footballmatchschedule.model.FavoriteMatch
import kotlinx.android.synthetic.main.holder_fragment_match.view.*
import org.jetbrains.anko.db.delete

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-26
 */
 
class FavoriteAdapter(private var favoriteMatch: ArrayList<FavoriteMatch>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    private lateinit var listener: ClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_fragment_match, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favoriteMatch.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(favoriteMatch[position])

        holder.itemView.layoutFavorite.setOnClickListener {
            holder.removeFromFavorite()
            if(holder.remove){
                removeItem(position)
            }
        }

        holder.itemView.layoutMatchTeam.setOnClickListener {
            listener.onClickItem(holder.idEvent)
        }
    }

    private fun removeItem(position: Int){
        favoriteMatch.removeAt(position)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: ClickItem) {
        this.listener = listener
    }

    interface ClickItem {
        fun onClickItem(id: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var remove: Boolean = false
        var idEvent: Int = 0
        private var idFavorite : Long = 0
        private var mappingImage: MappingImage = MappingImage()
        private val fullLove = itemView.context.resources.getDrawable(R.drawable.ic_favorite_black_24dp)

        fun bindData(data: FavoriteMatch){
            data.id?.let {
                idFavorite = it
            }
            data.idEvent.let {
                idEvent = it?.toInt()!!
            }
            itemView.textRound.text = "${data.round}"
            itemView.skorTeam1.text = "${data.skorHome}"
            itemView.skorTeam2.text = "${data.skorAway}"
            itemView.textEvent.text = "${data.date}"
            mappingImage.placingImage(itemView.context,"${data.homeBadge}", itemView.logoTeam1)
            mappingImage.placingImage(itemView.context, "${data.awayBadge}", itemView.logoTeam2)
            itemView.imageFavorite.setImageDrawable(fullLove)
        }

        fun removeFromFavorite() {
            try {
                itemView.context?.database?.use {
                    delete(FavoriteMatch.TABLE_FAVORITE, "_ID_ = {id}", "id" to idFavorite)
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