package pt.boldint.carbon.boldhunter.di.module

import dagger.Module
import dagger.Provides
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.presenter.main.MainPresenter
import pt.boldint.carbon.boldhunter.presenter.main.MainPresenterImpl
import pt.boldint.carbon.boldhunter.presenter.postdetails.PostDetailsPresenter
import pt.boldint.carbon.boldhunter.presenter.postdetails.PostDetailsPresenterImpl
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenter
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenterImpl

@Module
class PresentersModule {

    @Provides
    fun provideMainPresenter(postInteractor: PostInteractor): MainPresenter
            = MainPresenterImpl(postInteractor)

    @Provides
    fun providePostDetailsPresenter(postInteractor: PostInteractor): PostDetailsPresenter
            = PostDetailsPresenterImpl(postInteractor)

    @Provides
    fun provideUserDetailsPresenter(service: HunterService): UserDetailsPresenter
            = UserDetailsPresenterImpl(service)

}