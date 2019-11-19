package rainist.assignment.util

object ValidationUtil {

    fun checkEmail(email: String): Boolean =
        email.matches(
            Regex("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)\$")
        )

    /** @param pw1
     * this is first password
     * @param pw2
     * this is second password
     * @return check password state & password state equality
     * 
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     *
     * @Link https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
     */
    fun checkPassword(pw1: String, pw2: String): Boolean {
        return pw1.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$")) && pw1 == pw2
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

