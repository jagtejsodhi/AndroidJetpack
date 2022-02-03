package com.example.interiewprepandroidapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.interiewprepandroidapp.data.db.AppDB
import com.example.interiewprepandroidapp.data.model.Country
import com.example.interiewprepandroidapp.data.repository.CountryRepository
import kotlinx.coroutines.*

class SearchViewModel(private val database : AppDB) : ViewModel() {
    private val countryRepository = CountryRepository(database)

    private val _latestQuery = MutableLiveData("")
    val latestQuery : LiveData<String> = _latestQuery

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean> = _loading

    private val _filteredResults = MutableLiveData<List<Country>>()
    val filteredResults : LiveData<List<Country>> = _filteredResults

    private var currSearch : Job? = null

    init {
        onNewSearchQueryUpdated(_latestQuery.value!!)
    }

    fun onNewSearchQueryUpdated(userQuery : String) {
        this._latestQuery.value = userQuery

        currSearch?.cancel()

        currSearch = viewModelScope.launch {
            if (userQuery.isNotEmpty()) {
                // if user clears everything, we want to immediately show unfiltered list
                delay(500)
            }

            _loading.value = true

            // fake delay to show loading
            delay(300)

            val filteredItems = countryRepository.getCountries(userQuery)

            _loading.value = false

            // bc we're in view model scope, this will post on main thread
            _filteredResults.value = filteredItems
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(database : AppDB) : ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(database) as T
                }
            }
        }
    }

}