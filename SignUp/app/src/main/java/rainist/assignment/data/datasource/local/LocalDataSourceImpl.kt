package rainist.assignment.data.datasource.local

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.data.datasource.DataBase

class LocalDataSourceImpl(db: DataBase) : LocalDataSource {
    private val userDao = db.userDao()

    override fun saveUserInfo(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    override fun getUserInfo(): Single<UserEntity> {
        return userDao.select().subscribeOn(Schedulers.io())
    }
}