package pt.boldint.carbon.boldhunter.interactor.user

import pt.boldint.carbon.boldhunter.data.model.User

interface UserInteractor{

    suspend fun getUser(id:Int) : User

}
