package com.example.interiewprepandroidapp.data.repository

import com.example.interiewprepandroidapp.data.db.AppDB
import com.example.interiewprepandroidapp.data.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * Fetches data related to countries. This class should decide how to retrieve data - whether
 * it's from the cache or from
 */
class CountryRepository(private val database: AppDB) {
    suspend fun getCountries(filterQuery : String) : List<Country> {
        // at the beginning, function in running in the calling coroutine scope
        val backgroundScope = CoroutineScope(Dispatchers.IO)

        val countriesList = backgroundScope.async {
            // if this was a network call or DB read, do it in background coroutine scope
            // beauty of this is this scope won't get cancelled even if calling scope was cancelled
            // this is ideal bc even if UI disappears, we will finish network call and can cache data

            val countries = if (filterQuery.isNotEmpty()) {
                database.countryDao().loadAllByQuery(filterQuery.lowercase())
            } else {
                database.countryDao().getAll()
            }

            // countriesList.filter { it.value.lowercase().contains(filterQuery.lowercase()) }

            countries
        }

        // back to running in the calling coroutine scope
        return countriesList.await()
    }

}