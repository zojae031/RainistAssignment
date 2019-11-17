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

    fun checkPassword(pw1: String, pw2: String) = pw1 == pw2
    /**
     * TODO
     * 1. 특수문자, 영문자, 숫자 조합 -> 안정성 올라가기
     * 2. 동일숫자 3번 연속되면 막기 (ex : 111, 222...)
     * 3. 연속하는 숫자/글자 막기 (ex : abc, 123...)
     */
    fun checkName(name: String) = name.length < 10

    fun checkIdentifyFirst(id: String): Boolean = id.length == 6


    fun checkIdentifySex(id: String): IdentifyState {
        return when {
            id.startsWith('1') -> IdentifyState.MALE
            id.startsWith('2') -> IdentifyState.FEMALE
            id.startsWith('3') -> IdentifyState.MALE
            id.startsWith('4') -> IdentifyState.FEMALE
            else -> IdentifyState.ERROR
        }
    }

    fun checkIdentifyLast(id: String): Boolean = id.length == 7


    enum class IdentifyState {
        MALE, FEMALE, ERROR
    }
}

