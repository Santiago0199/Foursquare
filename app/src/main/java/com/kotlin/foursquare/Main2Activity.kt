package com.kotlin.foursquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class Main2Activity : AppCompatActivity() {

    var foursquare: Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        foursquare = Foursquare(this)

        getPlaces()
    }

    fun getPlaces(){
        val queue = Volley.newRequestQueue(this)
        var url = "https://api.foursquare.com/v2/venues/search?ll=40.7484,-73.9857&oauth_token="+ foursquare?.getToken() +"&v=20180109"

        var request = StringRequest(Request.Method.GET, url, Response.Listener { response ->

            Log.d("RESPONSE HTTP", response.toString())

            //==================PARSEO NATIVO==================//
            //var id = JSONObject(response).getJSONObject("response").getJSONArray("venues").getJSONObject(0).getString("id")
            //var name = JSONObject(response).getJSONObject("response").getJSONArray("venues").getJSONObject(0).getString("id")

            //==================PARSEO GSON==================//
            var res = Gson().fromJson(response, FoursqueareRequest::class.java)

            Log.d("RESPONSE HTTP X", res.response?.venues?.get(0)?.id)

        }, Response.ErrorListener { error ->

        })

        queue.add(request)
    }

}
