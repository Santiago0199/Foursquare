package com.kotlin.foursquare.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kotlin.foursquare.entities.Foursquare
import com.kotlin.foursquare.R

/**
 * @author Santiago Perdomo Forero
 */
class MainActivity : AppCompatActivity() {

    var fsq: Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bFoursquare = findViewById<Button>(R.id.foursquare)

        fsq = Foursquare(this)

        if(fsq!!.thereIsToken()){
            startActivity(Intent(this, Main2Activity::class.java))
            finish()
        }

        bFoursquare.setOnClickListener {
            fsq?.logIn()
        }
    }

    /**
     * Obtiene el resultado de las peticiones para hacer la conexión con foursqueare
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fsq?.validateActivityResult(requestCode, resultCode, data)
    }
}
