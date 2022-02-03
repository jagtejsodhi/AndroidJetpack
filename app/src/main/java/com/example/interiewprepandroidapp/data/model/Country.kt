package com.example.interiewprepandroidapp.data.model

import androidx.room.*

@Entity(tableName = "country")
data class Country(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "name") val value : String)


/**
 * Provides methods that app can use to access data in Country table
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countries : Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countries : List<Country>)

    @Delete
    fun delete(country : Country)
}


