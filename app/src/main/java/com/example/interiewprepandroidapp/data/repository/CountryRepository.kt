package com.example.interiewprepandroidapp.data.repository

import com.example.interiewprepandroidapp.data.db.AppDB
import com.example.interiewprepandroidapp.data.model.Country
import com.example.interiewprepandroidapp.data.network.UserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Fetches data related to countries. This class should decide how to retrieve data - whether
 * it's from the cache or from
 */
class CountryRepository(private val database: AppDB) {
    val backgroundScope = CoroutineScope(Dispatchers.IO)

    suspend fun getCountries(filterQuery : String) : List<Country> {
        // at the beginning, function in running in the calling coroutine scope

        val countriesList = withContext(Dispatchers.IO) {
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

        return countriesList

//        val countriesList = backgroundScope.async {
//            // beauty of this is this scope won't get cancelled even if calling scope was cancelled
//            // this is ideal bc even if UI disappears, we will finish network call and can cache data
//            val countries = if (filterQuery.isNotEmpty()) {
//                database.countryDao().loadAllByQuery(filterQuery.lowercase())
//            } else {
//                database.countryDao().getAll()
//            }
//
//            // countriesList.filter { it.value.lowercase().contains(filterQuery.lowercase()) }
//
//            countries
//        }
//
//        // back to running in the calling coroutine scope
//        return countriesList.await()
    }

    suspend fun fetchUsers() {
        val userRequest = UserRequest()
        val users = userRequest.fetchUserData("https://jsonplaceholder.typicode.com/users")
    }

}