package kade.submission2.footballmatchschedule.service.client

import io.reactivex.Observable
import kade.submission2.footballmatchschedule.model.*
import retrofit2.http.*

interface SportdbApi{
    // ambil semua list liga
    @GET("all_leagues.php")
    fun getAllLeague(): Observable<AllLeagueResponse>

    // ambil semua list team
    @GET("lookup_all_teams.php")
    fun getALLTeam(
        @Query("id") id: Int
    ): Observable<AllTeamResponse>

    // ambil detail liga
    @GET("lookupleague.php")
    fun getDetailLeague(
        @Query("id") id: Int
    ): Observable<DetailLeagueResponse>

    // ambil detail team
    @GET("lookupteam.php")
    fun getDetailTeam(
        @Query("id") id: Int
    ): Observable<DetailTeamResponse>

    // ambilevent sesuai id event
    @GET("lookupevent.php")
    fun getDetailEvent(
        @Query("id") id: Int
    ): Observable<EventResponse>

    //ambil event time
    @GET("searchevents.php")
    fun getSearchEvent(
        @Query("e") event: String
    ):Observable<SearchEventResponse>

    // ambil next event di league
    @GET("eventsnextleague.php")
    fun getNextEvent(
        @Query("id") id: Int
    ): Observable<EventResponse>

    // ambil past event di league
    @GET("eventspastleague.php")
    fun getPastEvent(
        @Query("id") id: Int
    ): Observable<EventResponse>

    // ambil daftar klasmen liga
    @GET("lookuptable.php")
    fun getClassement(
        @Query("l")  id: Int
    ): Observable<ClassementResponse>

    // ambil daftar pemain berdasarkan team
    @GET("lookup_all_players.php")
    fun getListPlayer(
        @Query("id") id: Int
    ): Observable<PlayerResponse>

    // pencarian team
    @GET ("searchteams.php")
    fun getSearchTeam(
        @Query("t") team: String
    ): Observable<SearchTeamResponse>

    // ambil detail pemain
    @GET("lookupplayer.php")
    fun getDetailPlayer(
        @Query("id") idPlayer: Int
    ): Observable<DetailPlayerResponse>

}