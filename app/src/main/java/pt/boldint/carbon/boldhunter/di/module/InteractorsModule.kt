package pt.boldint.carbon.boldhunter.di.module

import dagger.Module
import dagger.Provides
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractorImpl
import pt.boldint.carbon.boldhunter.interactor.user.UserInteractor
import pt.boldint.carbon.boldhunter.interactor.user.UserInteractorImpl
import pt.boldint.carbon.boldhunter.presenter.MainPresenter
import pt.boldint.carbon.boldhunter.presenter.main.MainPresenterImpl
import pt.boldint.carbon.boldhunter.presenter.postdetails.PostDetailsPresenter
import pt.boldint.carbon.boldhunter.presenter.postdetails.PostDetailsPresenterImpl
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenter
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenterImpl

@Module
class InteractorsModule {

    @Provides
    fun providePostInteractor(service: HunterService): PostInteractor
            = PostInteractorImpl(service)

    @Provides
    fun provideUserInteractor(service: HunterService): UserInteractor
            = UserInteractorImpl(service)
}