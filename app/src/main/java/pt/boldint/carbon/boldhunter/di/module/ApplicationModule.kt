package pt.boldint.carbon.boldhunter.di.module

import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import pt.boldint.carbon.boldhunter.BoldHunterApp
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: BoldHunterApp) {

    @Provides
    @Singleton
    fun provideApp(): BoldHunterApp = app

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

}
