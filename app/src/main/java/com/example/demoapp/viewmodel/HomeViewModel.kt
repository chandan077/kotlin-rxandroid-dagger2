package com.example.demoapp.viewmodel

/**
 * @author Chandan Kumar
 */
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.demoapp.interfaces.ResultCallback
import com.example.demoapp.model.AddressItem
import com.example.demoapp.repository.HomeRepository

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val mRepository: HomeRepository = HomeRepository(app)
    var mSearchResultList: ArrayList<String> = ArrayList()
    var mSearchStatus: MutableLiveData<String> = MutableLiveData<String>()

    companion object {
        const val SUCCESS_STR = "SUCCESS"
    }

    fun getSearchResults(searchStr: String) {
        mSearchStatus.run { setValue(null) }
        mRepository.fetchSearchResults(searchStr, object : ResultCallback<AddressItem> {

            override fun resultsLoaded(results: List<AddressItem>) {
                mSearchResultList.clear()
                for (addressItem in results) {
                    mSearchResultList.add(addressItem.addressString)
                }
                mSearchStatus.value = SUCCESS_STR
            }

            override fun onError(status: String?) {
                mSearchStatus.value = status
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.compositeDisposable.dispose()
    }
}