package com.example.lab4

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

data class ChuckNorrisJokeResponse(
    val categories: Array<Char>, val created_at: String, val icon_url:String,
    val id: String, val updated_at: String, val url: String, val value:String
)
class JokeRepo(val applicationContext: Context, val scope: CoroutineScope, val dao: JokeDAO) {
    val currJoke = dao.randomJoke().asLiveData()
    val allJokes = dao.allJokes().asLiveData()

    fun fetchJokeVolley(){
//        val url = "https://api.chucknorris.io/jokes/random"
        val url = Uri.Builder().scheme("https")
            .authority("api.chucknorris.io")
            .appendPath("jokes")
            .appendPath("random").build()

        val stringRequest = StringRequest(
            Request.Method.GET,
            url.toString(),
            {   response ->
                // parse and store data
                val jokeResult = Gson().fromJson(response, ChuckNorrisJokeResponse::class.java)
                // dao to store data
                scope.launch {
                dao.addJokeData(JokeData(Date(),jokeResult.value))
                }
            },
            {
                Log.e("Volley", "error fetching joke")
            }
        )
        // Adding request to the queue
        VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
    }
}