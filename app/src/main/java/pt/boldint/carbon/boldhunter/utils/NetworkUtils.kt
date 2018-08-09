package pt.boldint.carbon.boldhunter.utils


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import pt.boldint.carbon.boldhunter.BoldHunterApp


object NetworkUtils {


    fun isConnected():Boolean {
        val connMgr = BoldHunterApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }
    fun isWifiConnected():Boolean {
        return isConnected(ConnectivityManager.TYPE_WIFI)
    }
    fun isMobileConnected():Boolean {
        return isConnected(ConnectivityManager.TYPE_MOBILE)
    }
    @SuppressLint("ObsoleteSdkInt")
    private fun isConnected(type:Int):Boolean {
        val connMgr = BoldHunterApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            val networkInfo = connMgr.getNetworkInfo(type)
            networkInfo != null && networkInfo.isConnected
        }
        else isConnected(connMgr, type)

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun isConnected(connMgr:ConnectivityManager, type:Int):Boolean {
        val networks = connMgr.allNetworks
        var networkInfo:NetworkInfo
        for (mNetwork in networks)
        {
            networkInfo = connMgr.getNetworkInfo(mNetwork)
            if (networkInfo != null && networkInfo.type === type && networkInfo.isConnected)
            {
                return true
            }
        }
        return false
    }
}
