package com.kotlin.foursquare.adapters

import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.foursquare.R
import com.kotlin.foursquare.entities.Venue
import com.squareup.picasso.Picasso

/**
 * @author Santiago Perdomo Forero
 * Adaptador que asocia los datos del ArrayList con la vista
 */
class VenuesAdapter(list:ArrayList<Venue>):RecyclerView.Adapter<VenuesAdapter.ViewHolder>(){

    /**
     * ArrayList de lugares
     */
    var list: ArrayList<Venue>

    /**
     * Contexto
     */
    lateinit var context: Context

    /**
     * Inicialización de las variables
     */
    init{
        this.list = list
    }

    /**
     * Crea la vista de cada lugar por medio del template asociado
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        var view = LayoutInflater.from(parent.context).inflate(R.layout.template_venues, parent, false)
        var holder = ViewHolder(view)
        return holder
    }

    /**
     * Indica el tamaño del ArrayList de lugares
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Modifica los datos de los items segun cada posición del ArrayList
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list.get(position)
        holder.nameVenues?.text = item.name

        //La url con foursqueare debe ser con la estructura prefix + size + suffix
        if(item.categories!!.size > 0){
            var urlImage = item.categories?.get(0)!!.icon?.prefix + "bg_64" + item.categories?.get(0)!!.icon?.suffix
            //Picasso permite la carga de imágenes
            Picasso.with(context).load(urlImage).into(holder.image)
        }
    }

    /**
     * Clase donde se obtienen los componentes del template asociado al adaptador
     */
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        var nameVenues: TextView? = null
        var image: ImageView? = null
        init {
            nameVenues = view.findViewById(R.id.nameVenues) as TextView
            image = view.findViewById(R.id.imageVenues) as ImageView
        }
    }
}