package rainist.assignment.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import rainist.assignment.base.BaseDao

@Dao
interface UserDao : BaseDao<UserEntity> {
    @Query("Select * from UserEntity")
    fun select(): Single<UserEntity>
}
