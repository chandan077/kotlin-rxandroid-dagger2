package com.example.demoapp

/**
 * @author Chandan Kumar
 */
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.interfaces.AppConstants.FAILURE_STR
import com.example.demoapp.interfaces.AppConstants.NO_RESULT
import com.example.demoapp.interfaces.AppConstants.SUCCESS_STR
import com.example.demoapp.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), TextWatcher {

    private lateinit var viewModel: HomeViewModel
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.autocompleteTxtvw.threshold = 1

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.mSearchStatus.observe(this, addressResultObserver)
        dataBinding.autocompleteTxtvw.addTextChangedListener(this)
    }

    private var addressResultObserver: Observer<String> =
        Observer<String> { status ->
            when (status) {
                SUCCESS_STR -> showSearchResults()
                FAILURE_STR -> showErrorToast(false)
                NO_RESULT -> showErrorToast(true)
            }
        }

    private fun showErrorToast(isNoResult: Boolean) {
        if (isNoResult)
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, getString(R.string.no_result_found), Toast.LENGTH_LONG).show()

    }

    private fun showSearchResults() {
        var adapter =
            ArrayAdapter(this, R.layout.list_item_layout, viewModel.mSearchResultList)
        dataBinding.autocompleteTxtvw.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    override fun afterTextChanged(str: Editable?) {
        if (str.toString().isNotEmpty())
            if (Utility.isNetworkAvailable(this))
                viewModel.getSearchResults(str.toString())
            else
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
