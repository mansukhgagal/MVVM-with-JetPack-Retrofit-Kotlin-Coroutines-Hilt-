package com.example.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import timber.log.Timber

class SplashActivity : Activity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(SplashActivity::class.java.simpleName)

        startHomeActivity()
    }

    private fun startHomeActivity() {
        handler.postDelayed(
            runnable
            ,1000)
    }

    private val runnable =  Runnable {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun removeCallback() {
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        removeCallback()
    }

    override fun onResume() {
        super.onResume()
        removeCallback()
        startHomeActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeCallback()
    }

}