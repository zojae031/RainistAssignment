package rainist.assignment.util

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import rainist.assignment.util.ValidationUtil.IdentifyState

@BindingAdapter("setSex")
fun TextView.setSex(sex: IdentifyState) {
    text = when (sex) {
        IdentifyState.MALE -> {
            SEX_STATE[IdentifyState.MALE.ordinal].run {
                setTextColor(first)
                second
            }
        }
        IdentifyState.FEMALE -> {
            SEX_STATE[IdentifyState.FEMALE.ordinal].run {
                setTextColor(first)
                second
            }
        }
        IdentifyState.ERROR -> {
            SEX_STATE[IdentifyState.ERROR.ordinal].run {
                setTextColor(first)
                second
            }
        }
    }
}

@JvmField
val SEX_STATE = arrayOf(Color.BLUE to "남자", Color.RED to "여자", Color.GRAY to "-")