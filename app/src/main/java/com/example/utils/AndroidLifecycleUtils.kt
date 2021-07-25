package com.example.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment

object AndroidLifecycleUtils {
    fun canLoadImage(fragment: Fragment?): Boolean {
        if (fragment == null) {
            return false
        }

        val activity = fragment.activity

        return canLoadImage(activity)
    }

    fun canLoadImage(context: Context?): Boolean {
        if (context == null) {
            return false
        }

        if (context !is Activity) {
            return false
        }

        val activity = context as Activity?
        return canLoadImage(activity)
    }

    fun canLoadImage(activity: Activity?): Boolean {
        if (activity == null) {
            return false
        }

        val destroyed = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed

        return !(destroyed || activity.isFinishing)

    }
}