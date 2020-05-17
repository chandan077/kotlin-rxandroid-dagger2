package com.example.demoapp.interfaces

/**
 * @author Chandan Kumar
 */
interface ResultCallback<T> {
    fun resultsLoaded(results: List<T>)
    fun onError(status: String?)
}