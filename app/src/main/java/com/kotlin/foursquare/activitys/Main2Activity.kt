package com.kotlin.foursquare.activitys

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.kotlin.foursquare.entities.Venue
import org.json.JSONObject
import java.util.jar.Manifest

/**
 * @author Santiago Perdomo Forero
 */
class Main2Activity : AppCompatActivity() {

    var foursquare: Foursquare? = null
    var adapter: VenuesAdapter? = null
    lateinit var recycler: RecyclerView

    //=======Variables para la ublicación=========//
    private val permissLocationFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val CODE_LOCATION = 100
    var fusedClientLocation: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null
    var callback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        foursquare = Foursquare(this)

        recycler = findViewById(R.id.recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        fusedClientLocation = FusedLocationProviderClient(this)

        locationRequest = LocationRequest()
        locationRequest?.interval = 1000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onStart() {
        super.onStart()
        if(validateLocationPermissions()){
            getLocation()
        }else{
            requestPermissionLocation()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedClientLocation?.removeLocationUpdates(callback)
    }

    /**
     * Valida si la app tiene los permisos de ubicación
     */
    fun validateLocationPermissions(): Boolean{
        var locationFine = ActivityCompat.checkSelfPermission(this, permissLocationFine) == PackageManager.PERMISSION_GRANTED
        return locationFine
    }

    /**
     * Obtiene la ubicación del usuario.
     *
     * Como se debe validar si se tienen los permisos para utilizar lastLocation y en el metodo validateLocationPermissions
     * se esta haciendo entonces se pone la declarativa Missing para indicar que no se van a tener problemas
     */
    @SuppressLint("MissingPermission")
    fun getLocation(){
        callback = object : LocationCallback(){
            override fun onLocationResult(location: LocationResult?) {
                super.onLocationResult(location)
                for(latng in location!!.locations){
                    getPlaces(latng.latitude, latng.longitude)
                }
            }
        }
        fusedClientLocation?.requestLocationUpdates(locationRequest, callback, null)
    }

    /**
     * Realiza la solicitud de los permisos
     */
    fun requestPermissionLocation(){
        //var providerContext = ActivityCompat.shouldShowRequestPermissionRationale(this, permissionFineLocation)
        ActivityCompat.requestPermissions(this, arrayOf(permissLocationFine), CODE_LOCATION)
    }

    /**
     * Respuesta que se obtiene de la solicitud realizada
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CODE_LOCATION -> {
                //Si tiene resultados y si el resultado es que se concedio el permiso
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation()
                }else{
                    Toast.makeText(this, "No diste permiso para acceder a la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Obtiene los lugares cercanos segun los parametros dados para crear la lista
     * @param latitude donde nos encontramos
     * @param longitude donde nos encontramos
     */
    fun getPlaces(latitude: Double, longitude: Double){
        val queue = Volley.newRequestQueue(this)
        var url = "https://api.foursquare.com/v2/venues/search?ll=" + latitude + "," + longitude + "&oauth_token="+ foursquare?.getToken() +"&v=20180109"

        var request = StringRequest(Request.Method.GET, url, Response.Listener { response ->

            //var id = JSONObject(response).getJSONObject("response").getJSONArray("venues").getJSONObject(0).getString("id")
            var res = Gson().fromJson(response, FoursqueareRequest::class.java)
            adapter = VenuesAdapter(res.response!!.venues!!)
            recycler.adapter = adapter

        }, Response.ErrorListener { error ->

        })
        queue.add(request)
    }

}
