package com.example.interiewprepandroidapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interiewprepandroidapp.R
import com.example.interiewprepandroidapp.ui.main.SearchViewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchViewFragment.newInstance())
                .commitNow()
        }
    }
}