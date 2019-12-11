package kade.submission2.footballmatchschedule.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.model.Event
import kotlinx.android.synthetic.main.holder_daftar_liga.view.*
import kotlinx.android.synthetic.main.holder_fragment_match.view.*


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-19
 */

class AdapterMatch(
    val matchList: ArrayList<Event>,
    val context: Context
) : RecyclerView.Adapter<AdapterMatch.MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(context).inflate(R.layout.holder_fragment_match, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItemData(matchList[position], context)
    }

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItemData(eventMatch: Event, context: Context) {

        }

        fun mappingImage(context: Context, url: Uri, imageView: ImageView){
            Glide.with(context).load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_broken_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
                .into(imageView)
        }
    }

}