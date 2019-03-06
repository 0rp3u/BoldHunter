package pt.boldint.carbon.boldhunter.di.component


import dagger.Component
import pt.boldint.carbon.boldhunter.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    InteractorsModule::class

])

interface ApplicationComponent{
    fun presenters(): PresenterComponent

}
