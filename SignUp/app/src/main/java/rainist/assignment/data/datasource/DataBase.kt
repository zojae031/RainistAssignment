package rainist.assignment.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import rainist.assignment.data.dao.UserDao
import rainist.assignment.data.dao.UserEntity

@Database(
    entities = [UserEntity::class], version = 1
)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}