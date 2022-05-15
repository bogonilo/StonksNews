package com.lorenzo.stonksnews.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.lorenzo.stonksnews.R
import com.lorenzo.stonksnews.model.yfapi.StockHistory

fun StockHistory.getArrowDrawable(context: Context): Drawable? {
    return if (change > 0) {
        AppCompatResources.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_up_24)
    } else {
        AppCompatResources.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_down_24)
    }
}

fun Context.isInternetConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    var status = false

    connectivityManager?.let {
        if (it.getNetworkCapabilities(it.activeNetwork) != null) {
            // connected to the internet
            status = true
        }
    }

    return status
}

fun View.setBackgroundTint(@ColorRes colorRes: Int) {
    background.mutate().setTint(ContextCompat.getColor(context, colorRes))
}
