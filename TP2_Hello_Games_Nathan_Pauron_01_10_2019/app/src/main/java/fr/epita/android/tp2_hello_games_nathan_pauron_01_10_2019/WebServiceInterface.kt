package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Step 1: Create the Interface
interface WebServiceInterface {
    @GET("game/list")
    fun listAllGames(): Call<List<Game>>

    @GET("game/details")
    fun listDetailsForGame(@Query("game_id") game_id: Int): Call<List<GameDetails>>
}