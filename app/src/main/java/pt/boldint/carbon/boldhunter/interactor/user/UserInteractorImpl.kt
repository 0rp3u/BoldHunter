package pt.boldint.carbon.boldhunter.interactor.user

import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.data.toDomain

class UserInteractorImpl(private val service: HunterService) : UserInteractor {

    override suspend fun getUserDetails(id:Int) = service.getUserDetails(id).await().toDomain()
}
