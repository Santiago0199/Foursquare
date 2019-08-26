package com.kotlin.foursquare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.foursquare.android.nativeoauth.FoursquareOAuth

class Foursquare(var activity:AppCompatActivity) {

    private val CODE_CONNECT = 200
    private val CODE_EXCHANGE_CONNECT = 201

    private val CLIENT_ID = "BNSYVFSWU22SSBGLEVBIUWGW0COYRSF1JCQZLYTDJDIZY1YX"
    private val CLIENT_SECRET = "NA4ZNNQ4MDO54I30UMOELEZMVNPO1BIM04IRRGST4XR0YZLF"

    init {

    }

    fun logIn(){
        var intent = FoursquareOAuth.getConnectIntent(activity.applicationContext, CLIENT_ID)

        if(FoursquareOAuth.isPlayStoreIntent(intent)){
            //No se encontro la app Fourscare y se manda a la playstore para descargarla
            activity.startActivity(intent)
        }else{
            activity.startActivityForResult(intent, CODE_CONNECT)
        }
    }

    fun validateActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(requestCode){
            CODE_CONNECT -> fullConection(resultCode)
            CODE_EXCHANGE_CONNECT -> fullTokenExchange(resultCode)
        }
    }

    fun fullConection(resultCode: Int){
        
    }

    fun fullTokenExchange(resultCode: Int){

    }

}