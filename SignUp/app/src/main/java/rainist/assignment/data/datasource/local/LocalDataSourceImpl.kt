package rainist.assignment.data.datasource.local

import io.reactivex.Single
import rainist.assignment.data.dao.UserEntity

class LocalDataSourceImpl :LocalDataSource{
    override fun saveUserInfo(userEntity: UserEntity): Single<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}