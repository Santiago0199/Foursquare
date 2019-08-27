package com.kotlin.foursquare

class FoursqueareRequest{
    var response: FoursqueareResponse? = null
}

class FoursqueareResponse{
    var venues: ArrayList<Venues>? = null
}

class Venues{
    var id = ""
    var name = ""
}