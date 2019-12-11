package kade.submission2.footballmatchschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.helper.MappingImage
import kade.submission2.footballmatchschedule.model.Player
import kotlinx.android.synthetic.main.holder_list_player.view.*


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */
 
class AllPlayerAdapter (private val listPlayer: ArrayList<Player>): RecyclerView.Adapter<AllPlayerAdapter.ViewPlayer>() {
   private lateinit var listener: ClickPlayer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPlayer {
        return ViewPlayer(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_list_player, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPlayer.size
    }

    override fun onBindViewHolder(holder: ViewPlayer, position: Int) {
        holder.bindItemData(listPlayer[position], listener)
    }

    fun setOnClickPlayer(listener: ClickPlayer){
        this.listener = listener
    }

    interface ClickPlayer{
        fun setPlayer(idPlayer: Int)
    }
    class ViewPlayer(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val mappingImage = MappingImage()
        fun bindItemData(player: Player, listener: ClickPlayer){
            player.strCutout.let {
                mappingImage.placingImage(itemView.context, it, itemView.imagePlayer)
            }
            itemView.namePlayer.text = player.strPlayer
            itemView.setOnClickListener {
                listener.setPlayer(player.idPlayer)
            }
        }


    }
}