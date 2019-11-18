package rainist.assignment.data.datasource.local

import io.reactivex.Single
import rainist.assignment.data.dao.UserEntity

interface LocalDataSource {
    fun saveUserInfo(userEntity: UserEntity)
    fun getUserInfo(): Single<UserEntity>
}