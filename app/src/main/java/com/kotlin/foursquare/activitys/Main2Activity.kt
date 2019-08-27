package com.kotlin.foursquare.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kotlin.foursquare.entities.Foursquare
import com.kotlin.foursquare.entities.FoursqueareRequest
import com.kotlin.foursquare.R
import com.kotlin.foursquare.adapters.VenuesAdapter
import com.kotlin.foursquare.entities.Venues

class Main2Activity : AppCompatActivity() {

    var foursquare: Foursquare? = null
    var adapter: VenuesAdapter? = null
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        foursquare = Foursquare(this)


        recycler = findViewById(R.id.recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

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

            for(i in res.response!!.venues!!){
                Log.d("RESPONSE HTTP X", i.name)
            }
            Log.d("RESPONSE HTTP Y", res.response!!.venues!!.size.toString())
            adapter = VenuesAdapter(res.response!!.venues!!)
            recycler.adapter = adapter

        }, Response.ErrorListener { error ->

        })

        queue.add(request)
    }

}
