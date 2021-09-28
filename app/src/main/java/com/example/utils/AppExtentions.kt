package com.example.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun Context.getScreenWidth(): Int = resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int = resources.displayMetrics.heightPixels

fun Context.convertDpToPixel(dp: Float): Int {
    return (dp * (resources
        .displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Context.convertPixelsToDp(px: Float): Int {
    return (px / (resources
        .displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Context.fromColors(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getRectShape(colorRes: Int, radius: Float?=null): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(fromColors(colorRes))
    val raddis = FloatArray(8)
    raddis.fill(radius ?: 0f)
    gradientDrawable.cornerRadii = raddis
    return gradientDrawable
}

fun Context.launchUrl(url:String?) {
    url ?: return
    try {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    } catch (e:ActivityNotFoundException) {
        e.printStackTrace()
    }
}
