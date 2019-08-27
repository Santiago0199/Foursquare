package com.kotlin.foursquare.entities

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foursquare.android.nativeoauth.FoursquareOAuth
import com.kotlin.foursquare.activitys.Main2Activity

class Foursquare(var activity:AppCompatActivity) {

    private val CODE_CONNECT = 200
    private val CODE_EXCHANGE_CONNECT = 201

    private val CLIENT_ID = "BNSYVFSWU22SSBGLEVBIUWGW0COYRSF1JCQZLYTDJDIZY1YX"
    private val CLIENT_SECRET = "NA4ZNNQ4MDO54I30UMOELEZMVNPO1BIM04IRRGST4XR0YZLF"

    private val SETTINGS = "setttings"

    init {

    }

    /**
     * Se comunica con la Api para obtener la conexión entre la app y foursquare
     */
    fun logIn(){
        var intent = FoursquareOAuth.getConnectIntent(activity.applicationContext, CLIENT_ID)

        if(FoursquareOAuth.isPlayStoreIntent(intent)){
            //No se encontro la app Fourscare y se manda a la playstore para descargarla
            activity.startActivity(intent)
        }else{
            activity.startActivityForResult(intent, CODE_CONNECT)
        }
    }

    /**
     * Valida a que petición pertenece el codigo de respuesta
     */
    fun validateActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(requestCode){
            CODE_CONNECT -> fullConection(resultCode, data)
            CODE_EXCHANGE_CONNECT -> fullTokenExchange(resultCode, data)
        }
    }

    /**
     * Se valida qué codigo de respuesta tuvo el intento de conexión entre la app y foursqueare
     */
    fun fullConection(resultCode: Int, data: Intent?){
        val codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data)
        val exception = codeResponse.exception

        if(exception == null){
            val code = codeResponse.code
            performTokenExchange(code)
        }else{
            Toast.makeText(activity.applicationContext, "No se pudo realizar la conexión. Intentalo mas tarde...", Toast.LENGTH_SHORT).show()
            Log.d("ERRORS", exception.toString())
        }
    }

    /**
     * Se comunica con la Api para obtener el token de acceso por medio del codigo de respuesta de conexión
     */
    fun performTokenExchange(code: String){
        val intent = FoursquareOAuth.getTokenExchangeIntent(activity.applicationContext, CLIENT_ID, CLIENT_SECRET, code)
        activity.startActivityForResult(intent, CODE_EXCHANGE_CONNECT)
    }

    /**
     * Se valida qué codigo de respuesta tuvo la petición para obtener el token de acceso
     */
    fun fullTokenExchange(resultCode: Int, data: Intent?){
        val codeResponse = FoursquareOAuth.getTokenFromResult(resultCode, data)
        val exception = codeResponse.exception

        if(exception == null){
            val token = codeResponse.accessToken
            saveToken(token)
            changeActivity()
            Toast.makeText(activity.applicationContext, "Token: " + token, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(activity.applicationContext, "No se pudo realizar la conexión. Intentalo mas tarde...", Toast.LENGTH_SHORT).show()
            Log.d("ERRORS", exception.toString())
        }
    }

    /**
     * Guardar el token de acceso
     */
    fun saveToken(token: String){
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putString("token", token)
        editor.commit()
    }

    /**
     * Obtener el token de acceso
     */
    fun getToken():String{
        val settings = activity.getSharedPreferences(SETTINGS,0)
        val token = settings.getString("token", "")
        return token
    }

    /**
     * Verifica si hay un toquen o no guardado
     */
    fun thereIsToken(): Boolean{
        if(getToken() == ""){
            return false
        }else{
            return true
        }
    }

    /**
     * Cuando el token se encuentra guardado se cambia a la actividad 2
     */
    fun changeActivity(){
        activity.startActivity(Intent(activity.applicationContext, Main2Activity::class.java))
    }

}