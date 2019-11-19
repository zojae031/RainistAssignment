package rainist.assignment.util

object ValidationUtil {

    fun checkEmail(email: String): Boolean =
        email.matches(
            Regex("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)\$")
        )

    fun checkPassword(pw1: String, pw2: String): Boolean {
        return pw1.matches(Regex(return pw1.matches(Regex("^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$")) && pw1 == pw2)) && pw1 == pw2
    }

    fun checkName(name: String) = name.length < 10

    fun checkIdentify(id: String): Boolean =
        id.matches(Regex("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}\$"))


    fun checkIdentifySex(id: String): IdentifyState {
        return when {
            id.startsWith('1') -> IdentifyState.MALE
            id.startsWith('2') -> IdentifyState.FEMALE
            id.startsWith('3') -> IdentifyState.MALE
            id.startsWith('4') -> IdentifyState.FEMALE
            else -> IdentifyState.ERROR
        }
    }

    enum class IdentifyState {
        MALE, FEMALE, ERROR
    }

}

