package com.example.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import timber.log.Timber

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(SplashActivity::class.java.simpleName)

            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
            Timber.d("open main")
            finish()
    }
}