package kade.submission2.footballmatchschedule.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-08
 */

@Parcelize
data class DetailLeague(
    var idLeague: Int?,
    var strSport: String?,
    var strLeague: String?,
    var strDivision: String?,
    var strCountry: String?,
    var strLeagueAlternate: String?,
    var strWebsite: String?,
    var strFacebook: String?,
    var strTwitter: String?,
    var strDescriptionEN: String?,
    var strFanart1: String?,
    var strFanart2: String?,
    var strFanart3: String?,
    var strFanart4: String?,
    var strBadge: String?,
    var strPoster: String?,
    var strTrophy: String?,
    var strNaming: String?
): Parcelable