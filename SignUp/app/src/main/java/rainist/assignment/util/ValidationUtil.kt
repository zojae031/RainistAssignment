package rainist.assignment.util

object ValidationUtil {
    fun checkEmail(email: String): Boolean {
        val splitEmail = email.split("@")
        return when {
            splitEmail.size == 2 -> {
                splitEmail[splitEmail.lastIndex].split(".").run {
                    size == 2 && this[this.lastIndex].isNotEmpty()
                }
            }
            else -> false
        }
    }
}

