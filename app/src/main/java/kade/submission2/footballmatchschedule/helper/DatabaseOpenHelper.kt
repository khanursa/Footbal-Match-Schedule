package kade.submission2.footballmatchschedule.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kade.submission2.footballmatchschedule.model.FavoriteMatch
import kade.submission2.footballmatchschedule.model.TeamFavorite
import org.jetbrains.anko.db.*


/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-25
 */

class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavoriteMatch.TABLE_FAVORITE, true,
            FavoriteMatch._ID_ to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.ID_EVENT to TEXT,
            FavoriteMatch.ROUND to TEXT,
            FavoriteMatch.DATE to TEXT,
            FavoriteMatch.TEAM1_SKOR to TEXT,
            FavoriteMatch.TEAM2_SKOR to TEXT,
            FavoriteMatch.TEAM1_BADGE to TEXT,
            FavoriteMatch.TEAM2_BADGE to TEXT
        )

        db.createTable(
            TeamFavorite.TABLE_TEAM_FAVORITE, true,
            TeamFavorite.ID_ to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamFavorite.ID_TEAM to TEXT,
            TeamFavorite.IMAGE_TEAM to TEXT,
            TeamFavorite.NAME_TEAM to TEXT,
            TeamFavorite.DESC_TEAM to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)

        db.dropTable(TeamFavorite.TABLE_TEAM_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)