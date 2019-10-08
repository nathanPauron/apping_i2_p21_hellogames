package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Retrofit
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


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
                        // display performance optimization when list widget size does not change
                        RecyclerViewList.setHasFixedSize(true)
                        // here we specify this is a standard vertical list
                        RecyclerViewList.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.VERTICAL,
                            false)

                        val myItemClickListener = View.OnClickListener {
                            // we retrieve the row position fr0om its tag
                            if (it.tag != null) {
                            val position = it.tag!! as Int
                            val clickedItem = responseData[position]
                            //do stuff
                            val GameInfoIntent = Intent(this@MainActivity, GameInfoActivity::class.java)
                            GameInfoIntent.putExtra("game_id", clickedItem.id.toString())
                            startActivity(GameInfoIntent)
                            }
                        }
                        // attach an adapter and provide some data
                        RecyclerViewList.adapter = GameListAdapter(this@MainActivity, responseData, myItemClickListener)
                        RecyclerViewList.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                    }
                }
            }
        }
        //Step 4 : Call the WebService
        service.listAllGames().enqueue(wsCallback)
    }
}
