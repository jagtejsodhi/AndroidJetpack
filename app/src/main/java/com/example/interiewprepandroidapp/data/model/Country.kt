package com.example.interiewprepandroidapp.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "country")
data class Country(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "name") val value : String)


/**
 * Provides methods that app can use to access data in Country table
 *
 *  NOTE - we can make these suspend functions too
 */
@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun getAll(): List<Country>

    @Query("SELECT * FROM country WHERE id IN (:countryIds)")
    fun loadAllByIds(countryIds : IntArray) : List<Country>

    // || is append operator in sql
    @Query("SELECT * FROM country WHERE name LIKE '%' || :query || '%'")
    fun loadAllByQuery(query : String) : List<Country>

    // example for using suspend here!
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countries : Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countries : List<Country>)

    @Delete
    fun delete(country : Country)

    // example on how to use live data here
    @Query("SELECT * from country")
    fun observeAllCountries() : LiveData<List<Country>>

}


