package rainist.assignment.data.datasource.remote

import io.reactivex.Single
import rainist.assignment.data.dao.UserEntity

interface RemoteDataSource {
    fun requestSignUp(entity: UserEntity): Single<String>
}