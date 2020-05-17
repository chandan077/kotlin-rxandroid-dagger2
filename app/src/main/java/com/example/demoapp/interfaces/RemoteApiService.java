package com.example.demoapp.interfaces;

/**
 * @author Chandan Kumar
 */
import com.example.demoapp.response.AddressSearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteApiService {

    @GET("/compassLocation/rest/address/autocomplete")
    Observable<AddressSearchResponse> fetchSearchResults(@Query("queryString") String searchText, @Query("city") String city);
}
