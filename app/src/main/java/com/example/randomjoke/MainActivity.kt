package com.example.randomjoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

fun JSONObject.contains(key: String): Boolean {
    return this.has(key)
}

class MainActivity : AppCompatActivity() {
    var jokeTextURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.generateButton)
        val joke = findViewById<TextView>(R.id.jokeText)

        button.setOnClickListener {
            getNextJoke(joke)
        }

        Log.d("jokeTextURL", "Joke Text URL set")
    }

    private fun getJoke(textView: TextView) {
        val client = AsyncHttpClient()
        client["https://v2.jokeapi.dev/joke/Any?blacklistFlags=religious,political", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                if (json.jsonObject.contains("joke")) {
                    jokeTextURL = json.jsonObject.getString("joke")
                } else {
                    val jokeTextSetup = json.jsonObject.getString("setup")
                    val jokeTextDelivery = json.jsonObject.getString("delivery")
                    jokeTextURL = jokeTextSetup + "\n\n\n\n" + jokeTextDelivery
                }
                runOnUiThread {
                    textView.text = jokeTextURL
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

    private fun getNextJoke(textView: TextView) {
        getJoke(textView)
    }
}
