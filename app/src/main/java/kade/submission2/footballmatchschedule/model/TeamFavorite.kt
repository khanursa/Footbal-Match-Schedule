package kade.submission2.footballmatchschedule.model


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-30
 */

data class TeamFavorite(
    val id: Long?,
    val idTeam: String?,
    val image: String?,
    val name: String?,
    val descTeam: String?
) {
    companion object {
        const val TABLE_TEAM_FAVORITE: String = "TABLE_TEAM_FAVORITE"
        const val ID_: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
        const val IMAGE_TEAM: String = "IMAGE_TEAM"
        const val NAME_TEAM: String = "NAME_TEAM"
        const val DESC_TEAM: String = "DESC_TEAM"
    }
}