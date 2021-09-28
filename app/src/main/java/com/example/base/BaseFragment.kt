package com.example.base

import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.example.utils.gone
import com.example.utils.visible

open class BaseFragment: Fragment() {

    fun showProgress(progressbar:ContentLoadingProgressBar) {
        progressbar.visible()
    }
    fun hideProgress(progressbar:ContentLoadingProgressBar) {
        progressbar.gone()
    }


}