package com.peod_stock.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.peod_stock.R
import com.peod_stock.base.MyApp

class SnackBarHelper {
    companion object {

        fun infoSnackBar(contextView: View, text: String) {
            infoSnackBar(contextView, text, MyApp.instance.getString(R.string.snackbar_action_ok))
        }

        fun infoSnackBar(contextView: View, text: String, action: String) {
            Snackbar.make(contextView, text, Snackbar.LENGTH_LONG)
                .setAction(action) {
                    // Responds to click on the action
                }
                .show()
        }
    }
}