package com.example.interiewprepandroidapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.interiewprepandroidapp.data.model.Country
import com.example.interiewprepandroidapp.data.model.CountryDao

// Specify all entities in this array
@Database(entities = [Country::class], version = 1)
abstract class AppDB : RoomDatabase() {
    // Create no param abstarct methods that return Dao's for each entity
    abstract fun countryDao() : CountryDao
}