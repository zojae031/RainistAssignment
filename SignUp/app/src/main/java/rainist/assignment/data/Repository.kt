package rainist.assignment.data

import io.reactivex.Single
import rainist.assignment.data.dao.UserEntity

interface Repository {
    fun requestSignUp(entity: UserEntity): Single<String>
    fun getUserInfo(): Single<UserEntity>
}