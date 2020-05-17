package com.example.demoapp.model

/**
 * @author Chandan Kumar
 */
data class AddressItem(
    var id: String,
    var city: String,
    var addressString: String,
    var latitude: Double,
    var longitude: Double,
    var label: String
)