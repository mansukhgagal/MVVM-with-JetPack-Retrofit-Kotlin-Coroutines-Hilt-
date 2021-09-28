package com.example.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

class Utils {

    companion object {
        fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return result
        }

        val imageUrls = listOf<String>(
            "https://iconarchive.com/download/i86411/martin-berube/flat-animal/bird.ico",
            "https://images.vexels.com/media/users/3/218835/isolated/lists/e122435c2f8cca2266564dd77450046c-flying-blue-bird-illustration.png",
            "https://i.pinimg.com/originals/28/d6/39/28d639015c5d48b8d7c5697a7db5e19d.png",
            "https://img.icons8.com/emoji/256/bird-emoji.png",
            "https://images.vexels.com/media/users/3/218835/isolated/lists/e122435c2f8cca2266564dd77450046c-flying-blue-bird-illustration.png",
            "https://images.vexels.com/media/users/3/219948/isolated/lists/57ad0751075aa1bd9530fb3a9ae86720-parrot-macaw-bird-flying.png",
            "https://i.pinimg.com/originals/a7/a6/33/a7a63390a8010f5666108a05811e760b.jpg",
            "https://images.vexels.com/media/users/3/145486/isolated/lists/ef2ff91d74546bd2eb5794522bd542f9-bird-sketch.png",
            "https://i1.wp.com/novocom.top/image/Y2xpc1hZy5GFydG1hZy5jb20=/image/yellow-bird-drawing-33.jpg",
            "https://images.vexels.com/media/users/3/203292/isolated/lists/26f0027e7bc21a6e5c3284b2f7e0f5eb-mountain-bluebird-bird-stroke.png",
            "https://pbs.twimg.com/profile_images/2984099803/dc0bfe87368b73f8def3cb70e1d57b17.jpeg"

        )
        var current = 0
        val count = imageUrls.size
        fun randomURL() :String? {
            return imageUrls[current++%count]
        }

    }

}