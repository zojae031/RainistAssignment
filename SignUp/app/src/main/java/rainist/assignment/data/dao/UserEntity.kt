package rainist.assignment.data.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.JsonArray
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import rainist.assignment.util.DataConverterUtil

@Entity
@Parcelize
@TypeConverters(DataConverterUtil::class)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val name: String,
    val pId: String,
    val sex: Int,
    val permission: @RawValue JsonArray
) : Parcelable

