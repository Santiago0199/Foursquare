package com.kotlin.foursquare.adapters

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.foursquare.R
import com.kotlin.foursquare.entities.Venues

class VenuesAdapter(list:ArrayList<Venues>):RecyclerView.Adapter<VenuesAdapter.ViewHolder>(){

    var list: ArrayList<Venues>

    init{
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.template_venues, parent, false)
        var holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list.get(position)
        holder.nameVenues?.text = item.name
    }


    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        var nameVenues: TextView? = null
        init {
            nameVenues = view.findViewById(R.id.nameVenues) as TextView
        }
    }
}