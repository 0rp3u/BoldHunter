package pt.boldint.carbon.boldhunter.di.component

import dagger.Subcomponent
import pt.boldint.carbon.boldhunter.di.module.PresentersModule
import pt.boldint.carbon.boldhunter.view.main.MainActivity
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsActivity
import pt.boldint.carbon.boldhunter.view.userdetails.UserDetailsActivity


@Subcomponent(modules = [PresentersModule::class])
interface PresenterComponent {

    fun injectTo(Activity: MainActivity)
    fun injectTo(Activity: PostDetailsActivity)
    fun injectTo(Activity: UserDetailsActivity)

}