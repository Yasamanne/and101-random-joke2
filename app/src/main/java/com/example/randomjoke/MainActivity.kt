package com.example.randomjoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

fun JSONObject.contains(key: String): Boolean {
    return this.has(key)
}

class MainActivity : AppCompatActivity() {
    var jokeTextURL = ""
    private lateinit var recyclerViewJokes: RecyclerView
    var jokesList = mutableListOf<String>() //list of photo URLs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewJokes = findViewById<RecyclerView>(R.id.jokes_recycler_view)


        getJoke(15)
    }



    private fun getJoke(count: Int) {
        val client = AsyncHttpClient()
        for (i in 1..count) {
            client["https://v2.jokeapi.dev/joke/Any?blacklistFlags=religious,political", object :
                JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    val jokeTextObject = json.jsonObject

                    if (jokeTextObject.contains("joke")) {
                        jokeTextURL = jokeTextObject.getString("joke")
                    } else {
                        val jokeTextSetup = jokeTextObject.getString("setup")
                        val jokeTextDelivery = jokeTextObject.getString("delivery")
                        jokeTextURL = jokeTextSetup + "\n\n\n\n" + jokeTextDelivery
                    }

                    jokesList.add(jokeTextURL)
                    Log.d("sib", jokeTextURL)

                    if (jokesList.size == count) {
                        // All jokes have been fetched, update the UI
                        val adapter = JokeAdapter(jokesList)
                        recyclerViewJokes.adapter = adapter
                        recyclerViewJokes.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerViewJokes.addItemDecoration(
                            DividerItemDecoration(
                                this@MainActivity,
                                LinearLayoutManager.VERTICAL
                            )
                        )
                    }
                    Log.d("Joke", "response successful")
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Joke Error", errorResponse)
                }
            }]
        }
    }
}
