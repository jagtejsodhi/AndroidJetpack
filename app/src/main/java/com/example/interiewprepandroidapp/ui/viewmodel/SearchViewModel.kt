package com.example.interiewprepandroidapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.interiewprepandroidapp.data.model.SearchItem
import com.example.interiewprepandroidapp.data.repository.CountryRepository
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {
    private val countryRepository = CountryRepository()

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean> = _loading

    private val _filteredResults = MutableLiveData<List<SearchItem>>()
    val filteredResults : LiveData<List<SearchItem>> = _filteredResults

    private var currSearch : Job? = null

    init {
        onNewSearchQueryUpdated("")
    }

    fun onNewSearchQueryUpdated(userQuery : String) {
        currSearch?.cancel()

        currSearch = viewModelScope.launch {

            if (userQuery.isNotEmpty()) {
                // if user clears everything, we want to immediately show unfiltered list
                delay(500)
            }

            _loading.value = true

            // fake delay to show loading
            // delay(500)

            val filteredItems = countryRepository.getCountries(userQuery)

            _loading.value = false

            // bc we're in view model scope, this will post on main thread
            _filteredResults.value = filteredItems
        }
    }

}