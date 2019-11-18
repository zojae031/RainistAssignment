package rainist.assignment.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val pId: String,
    val sex: Int,
    val permission: Array<Boolean>
)