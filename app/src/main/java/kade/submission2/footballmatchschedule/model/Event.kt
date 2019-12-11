package kade.submission2.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    val idEvent: Int,
    val strEvent: String,
    val idLeague: String,
    val strSport: String,
    val intRound: Int,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeShots: Int,
    val intAwayShots: Int,
    val intHomeScore: Int,
    val intAwayScore: Int,
    val strHomeGoalDetails: String,
    val strAwayGoalDetails: String,
    val strHomeFormation: String,
    val strAwayFormation: String,
    val strHomeYellowCards: String,
    val strAwayYellowCards: String,
    val strAwayRedCards: String,
    val strHomeRedCards: String,
    val strHomeLineupGoalkeeper: String,
    val strHomeLineupDefense: String,
    val strHomeLineupMidfield: String,
    val strHomeLineupForward: String,
    val strHomeLineupSubstitutes: String,
    val strAwayLineupGoalkeeper: String,
    val strAwayLineupDefense: String,
    val strAwayLineupMidfield: String,
    val strAwayLineupForward: String,
    val strAwayLineupSubstitutes: String,
    val dateEvent: String,
    val strTime: String,
    val idHomeTeam: String,
    val idAwayTeam: String,
    val strThumb: String
):Parcelable
