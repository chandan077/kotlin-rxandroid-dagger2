package com.example.demoapp.model

/**
 * @author Chandan Kumar
 */
data class ResponseData(
    var autoCompleteRequestString: String,
    var addressList: ArrayList<AddressItem>
)