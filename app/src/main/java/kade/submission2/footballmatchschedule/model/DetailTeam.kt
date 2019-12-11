package kade.submission2.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTeam(
    val idTeam: Int,
    val strTeam: String,
    val strAlternate: String,
    val strLeague: String,
    val idLeague: String,
    val strStadium: String,
    val strStadiumThumb: String,
    val strStadiumDescription: String,
    val strStadiumLocation: String,
    val intStadiumCapacity: Int,
    val strWebsite: String,
    val strFacebook: String,
    val strTwitter: String,
    val strInstagram: String,
    val strDescriptionEN: String,
    val strCountry: String,
    val strTeamBadge: String,
    val strTeamJersey: String,
    val strTeamLogo: String,
    val strTeamFanart1: String,
    val strTeamFanart2: String,
    val strTeamFanart3: String,
    val strTeamFanart4: String,
    val strTeamBanner: String,
    val strYoutube: String
):Parcelable