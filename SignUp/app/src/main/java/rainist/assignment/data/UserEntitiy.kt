package rainist.assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntitiy(
    @PrimaryKey val id: String,
    val email: String,
    val password: String,
    val name: String,
    val pId: String,
    val sex: Boolean,
    val permission: Triple<Boolean, Boolean, Boolean>
)