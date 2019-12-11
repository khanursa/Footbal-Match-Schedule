package kade.submission2.footballmatchschedule.model


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-25
 */

data class FavoriteMatch(
    val id: Long?,
    val idEvent: String?,
    val round: String?,
    val date: String?,
    val skorHome: String?,
    val skorAway: String?,
    val homeBadge: String?,
    val awayBadge: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val _ID_: String = "_ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val ROUND: String = "ROUND"
        const val DATE: String = "DATE"
        const val TEAM1_SKOR: String = "TEAM1_SKOR"
        const val TEAM2_SKOR: String ="TEAM2_SKOR"
        const val TEAM1_BADGE: String = "TEAM1_BADGE"
        const val TEAM2_BADGE: String = "TEAM2_BADGE"
    }
}