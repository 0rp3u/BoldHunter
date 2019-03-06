package pt.boldint.carbon.boldhunter

import android.app.Application
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import pt.boldint.carbon.boldhunter.di.component.ApplicationComponent
import pt.boldint.carbon.boldhunter.di.component.DaggerApplicationComponent
import pt.boldint.carbon.boldhunter.di.module.ApplicationModule
import pt.boldint.carbon.boldhunter.di.module.NetworkModule


open class BoldHunterApp : Application() {

    companion object{
        lateinit var instance: BoldHunterApp
            private set
    }

    open val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule("https://api.producthunt.com/v1/", "b5ceb1eaa90382ad18cfa8830e634a6fc66a608c876baf43a6563f6bd976b59a" ))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) setupLeakCanary()
        instance = this
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        enabledStrictMode()
        LeakCanary.install(this)
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .permitDiskReads()//
                .penaltyLog() //
                .penaltyDeath() //
                .build())
    }
}