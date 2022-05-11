package com.lorenzo.stonksnews.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

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
