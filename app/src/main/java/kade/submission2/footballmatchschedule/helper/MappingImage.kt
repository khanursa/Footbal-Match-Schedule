package kade.submission2.footballmatchschedule.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kade.submission2.footballmatchschedule.R


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-24
 */
 
open class MappingImage{
    fun placingImage(context: Context, url: String, image: ImageView){
        Glide.with(context)
            .load(url?: "https://www.notimage.com")
            .apply(RequestOptions
                .placeholderOf(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp))
            .into(image)
    }
}