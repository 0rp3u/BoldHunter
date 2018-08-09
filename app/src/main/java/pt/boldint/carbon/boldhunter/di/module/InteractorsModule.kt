package pt.boldint.carbon.boldhunter.di.module

import dagger.Module
import dagger.Provides
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractorImpl
import pt.boldint.carbon.boldhunter.interactor.user.UserInteractor
import pt.boldint.carbon.boldhunter.interactor.user.UserInteractorImpl

@Module
class InteractorsModule {

    @Provides
    fun providePostInteractor(service: HunterService): PostInteractor
            = PostInteractorImpl(service)

    @Provides
    fun provideUserInteractor(service: HunterService): UserInteractor
            = UserInteractorImpl(service)
}