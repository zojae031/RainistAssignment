package rainist.assignment.data

import io.reactivex.Single
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.data.datasource.local.LocalDataSource
import rainist.assignment.data.datasource.remote.RemoteDataSource

class RepositoryImpl(private val remote: RemoteDataSource, private val local: LocalDataSource) :
    Repository {
    override fun requestSignUp(entity: UserEntity): Single<String> {
        return remote.requestSignUp(entity).doOnSuccess { local.saveUserInfo(entity) }
    }

    override fun getUserInfo(): Single<UserEntity> {
        return local.getUserInfo()
    }
}