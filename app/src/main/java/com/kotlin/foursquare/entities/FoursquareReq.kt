package com.kotlin.foursquare.entities

/**
 * @author Santiago Perdomo Forero
 * Clases para hacer el parseo del JSON que se obtiene en la solicitud http de lugares
 */

class FoursqueareRequest{
    var response: FoursqueareResponse? = null
}

class FoursqueareResponse{
    var venues: ArrayList<Venue>? = null
}

class Venue{
    var id = ""
    var name = ""
    var categories: ArrayList<Category>? = null
}

class Category{
    var id = ""
    var name = ""
    var icon: Icon? = null
}

class Icon{
    var prefix = ""
    var suffix = ""
}