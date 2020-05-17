package com.example.demoapp.repository

/**
 * @author Chandan Kumar
 */
import android.app.Application
import com.example.demoapp.MyApplication
import com.example.demoapp.interfaces.AppConstants.CITY_GURGAON
import com.example.demoapp.interfaces.AppConstants.FAILURE_STR
import com.example.demoapp.interfaces.AppConstants.NO_RESULT
import com.example.demoapp.interfaces.RemoteApiService
import com.example.demoapp.interfaces.ResultCallback
import com.example.demoapp.model.AddressItem
import com.example.demoapp.response.AddressSearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeRepository(application: Application) {

    private var remoteService: RemoteApiService =
        (application as MyApplication).getDemoAppComponent()!!.remoteApiService

    private lateinit var callback: ResultCallback<AddressItem>
    val compositeDisposable = CompositeDisposable()

    fun fetchSearchResults(searchStr: String, callback: ResultCallback<AddressItem>) {
        this.callback = callback

        val addressObservable: Observable<AddressSearchResponse> =
            remoteService.fetchSearchResults(searchStr, CITY_GURGAON)

        compositeDisposable.add(addressObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { result -> result.data.addressList }
            .subscribe(this::handleResults, this::handleError))
    }

    private fun handleResults(addressList: List<AddressItem>) {
        if (addressList.isNotEmpty()) {
            callback.resultsLoaded(addressList)
        } else {
            callback.onError(NO_RESULT)
        }
    }

    private fun handleError(t: Throwable) {
        callback.onError(FAILURE_STR)
    }
}