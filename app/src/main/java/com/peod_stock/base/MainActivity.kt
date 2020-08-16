package com.peod_stock.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peod_stock.R
import com.peod_stock.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        Timber.tag(MainActivity::class.java.simpleName)
        Timber.d("MainActivity")

    }
}






