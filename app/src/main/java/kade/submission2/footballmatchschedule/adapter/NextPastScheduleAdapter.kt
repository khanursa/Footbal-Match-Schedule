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
import kade.submission2.footballmatchschedule.contract.TeamContract
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.helper.database
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.model.FavoriteMatch
import kade.submission2.footballmatchschedule.presenter.TeamPresenter
import kotlinx.android.synthetic.main.holder_fragment_match.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-20
 */

class NextPastScheduleAdapter(var event: ArrayList<Event>) :
    RecyclerView.Adapter<NextPastScheduleAdapter.ViewHolderSchedule>() {
    private lateinit var lDetail: ClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSchedule {
        return ViewHolderSchedule(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_fragment_match, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: ViewHolderSchedule, position: Int) {
        holder.bindItemData(event[position], lDetail)
    }

    fun setOnClickListener(lDetail: ClickItem) {
        this.lDetail = lDetail
    }

    interface ClickItem {
        fun onClickItem(id: Int)
    }


    class ViewHolderSchedule(itemView: View) :
        RecyclerView.ViewHolder(itemView), TeamContract.View {
        lateinit var presenter: TeamPresenter
        private var loads: Boolean = false
        private val tags: String = "NPSAdapter"
        private var isFavorite: Boolean = false
        private lateinit var homeBadge: String
        private lateinit var awayBadge: String
        private lateinit var round: String
        private lateinit var hSkor: String
        private lateinit var wSkor: String
        private lateinit var date: String
        private lateinit var id: String
        private var mappingImage: MappingImage = MappingImage()
        private val fullLove = itemView.context.resources.getDrawable(R.drawable.ic_favorite_black_24dp)
        private val love = itemView.context.resources.getDrawable(R.drawable.ic_favorite_border_black_24dp)


        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bindItemData(event: Event, mDetail: ClickItem) {
            presenter = TeamPresenter(this)
            round = "Round ${event.intRound}"
            hSkor = "${event.intHomeScore}"
            wSkor = "${event.intAwayScore}"
            id = "${event.idEvent}"

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("GMT +7")
            val localDate = format.parse("${event.dateEvent} ${event.strTime}")
            date = "$localDate"

            itemView.textRound.text = round
            itemView.skorTeam1.text = hSkor
            itemView.skorTeam2.text = wSkor
            itemView.textEvent.text = date

            presenter.detailTeam(event.idHomeTeam.toInt(), true)

            presenter.detailTeam(event.idAwayTeam.toInt(), false)

            itemView.layoutMatchTeam.setOnClickListener {
                mDetail.onClickItem(id.toInt())
            }

            favoriteState()

            if (isFavorite) {
                itemView.imageFavorite.setImageDrawable(fullLove)
            } else {
                itemView.imageFavorite.setImageDrawable(love)
            }

            isFavorite = false

            itemView.layoutFavorite.setOnClickListener {
                Log.d("FAVORITE", "fullLove : $fullLove\nlove : $love")
                if (itemView.imageFavorite.drawable == fullLove) {
                    removeFromFavorite()
                    itemView.imageFavorite.setImageDrawable(love)
                } else {
                    addFavorite()
                    itemView.imageFavorite.setImageDrawable(fullLove)
                }

            }
        }

        private fun toastMessage(message: String) {
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }

        private fun addFavorite() {
            try {
                itemView.context.database.use {
                    insert(
                        FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.ID_EVENT to id,
                        FavoriteMatch.ROUND to round,
                        FavoriteMatch.DATE to date,
                        FavoriteMatch.TEAM1_SKOR to hSkor,
                        FavoriteMatch.TEAM2_SKOR to wSkor,
                        FavoriteMatch.TEAM1_BADGE to homeBadge,
                        FavoriteMatch.TEAM2_BADGE to awayBadge
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
                val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs(
                        "(ID_EVENT = {id})",
                        "id" to id
                    )
                val favorite = result.parseList(classParser<FavoriteMatch>())
                if (favorite.isNotEmpty()) isFavorite = true
            }
        }

        private fun removeFromFavorite() {
            try {
                itemView.context.database.use {
                    delete(FavoriteMatch.TABLE_FAVORITE, "ID_EVENT = {eventId}", "eventId" to id)
                }
                toastMessage("success remove favorite")
            } catch (e: SQLiteConstraintException) {
                Log.d("FAVORITE", "remove Favorite : ${e.message}")
                toastMessage("Failled remove favorite from database")
            }
        }

        override fun loadTeamSuccess(home: Boolean) {
            if (home) {
                presenter.getTeam()?.strTeamBadge?.let {
                    mappingImage.placingImage(itemView.context, it, itemView.logoTeam1)
                    homeBadge = it
                }
            } else {
                presenter.getTeam()?.strTeamBadge?.let {
                    mappingImage.placingImage(itemView.context, it, itemView.logoTeam2)
                    awayBadge = it
                }
            }


        }

        override fun loadTeamFailled(message: String) {
            loads = false
            Log.d(tags, message)
        }

        override fun progressLoad(load: Boolean) {

        }
    }
}