package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Step 2: Create the Retrofit and service client objects
        // A List to store or objects
        val data = arrayListOf<Game>()
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
        val wsCallback: Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<Game>>, response:
            Response<List<Game>>
            ) {
                Log.d("TAG", "WebService pass here : " +  response.code())
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.d("TAG", "WebService success : " + responseData.size)
                        val game_1 = (responseData.indices).random()
                        Glide
                            .with(this@MainActivity)
                            .load(responseData[0].picture)
                            .into(findViewById(R.id.game_img_1))

                        Glide
                            .with(this@MainActivity)
                            .load(responseData[1].picture)
                            .into(findViewById(R.id.game_img_2))

                        Glide
                            .with(this@MainActivity)
                            .load(responseData[2].picture)
                            .into(findViewById(R.id.game_img_3))

                        Glide
                            .with(this@MainActivity)
                            .load(responseData[3].picture)
                            .into(findViewById(R.id.game_img_4))
                    }
                }
            }
        }
        //Step 4 : Call the WebService
        service.listAllGames().enqueue(wsCallback)
        //Callback(0).picture
    }
}
