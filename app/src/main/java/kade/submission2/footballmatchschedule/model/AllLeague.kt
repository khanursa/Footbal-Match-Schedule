package kade.submission2.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AllLeague (
    val idLeague: Int,
    val strLeague: String,
    val strSport: String
):Parcelable
