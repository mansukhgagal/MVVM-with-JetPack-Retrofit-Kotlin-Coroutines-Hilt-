package com.example.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.example.R
import com.example.base.MyApp

class SnackBarHelper {
    companion object {

        private var snackbar: Snackbar? = null

        fun infoSnackBar(contextView: View, text: String) {
            infoSnackBar(contextView, text, MyApp.instance.getString(R.string.snackbar_action_ok))
        }

        fun infoSnackBar(
            contextView: View,
            text: String,
            action: String,
            callback: View.OnClickListener? = null
        ) {
            if (snackbar != null && snackbar!!.isShown) return
            snackbar = Snackbar.make(contextView, text, Snackbar.LENGTH_LONG)
                .setAction(action) {
                    callback?.onClick(it)
                }
            snackbar?.show()
        }
    }
}