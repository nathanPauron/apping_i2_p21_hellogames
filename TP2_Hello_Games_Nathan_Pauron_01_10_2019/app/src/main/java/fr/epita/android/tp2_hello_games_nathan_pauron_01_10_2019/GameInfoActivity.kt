package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebStorage
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)

        val game_id = intent.getStringExtra("game_id")
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        //Step 3: The Callback object
        val wsCallback: Callback<GameDetails> = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<GameDetails>, response:
            Response<GameDetails>
            ) {
                Log.d("TAG", "WebService pass here : " +  response.code())
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    val GameImage : ImageView = findViewById(R.id.gameImageView)
                    if (responseData != null) {
                        Glide
                            .with(this@GameInfoActivity)
                            .load(responseData.picture)
                            .into(GameImage)
                        val GameGeneralities : TextView = findViewById(R.id.genTextView)
                        val name : String       = "Name : " + responseData.name
                        val type : String       = "Type : " + responseData.type
                        val nbPlayer : String   = "Nb Players : " + responseData.players
                        val year : String       = "Year : " + responseData.year
                        GameGeneralities.text = name + "\n" + type + "\n" + nbPlayer + "\n" + year

                        val GameDescription_en : TextView = findViewById(R.id.descriptionTextView)
                        GameDescription_en.text = responseData.description_en
                    }
                }
            }
        }
        //Step 4 : Call the WebService
        //service.listAllGames().enqueue(wsCallback)
        service.listDetailsForGame(game_id.toInt()).enqueue(wsCallback)
    }
}
