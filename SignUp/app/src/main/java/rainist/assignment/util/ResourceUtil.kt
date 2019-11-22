package rainist.assignment.util

import android.content.res.Resources
import androidx.annotation.StringRes

class ResourceUtil(private val resource: Resources) {
    fun getResource(@StringRes id: Int) = resource.getString(id)
}

