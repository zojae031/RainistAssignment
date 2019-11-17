package rainist.assignment.util

object ValidationUtil {
    fun checkEmail(email: String): Boolean =
        email.matches(
            Regex("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)\$")
        )


    fun checkPassword(pw1: String, pw2: String) = pw1 == pw2
    /**
     * TODO
     * 1. 특수문자, 영문자, 숫자 조합 -> 안정성 올라가기
     * 2. 동일숫자 3번 연속되면 막기 (ex : 111, 222...)
     * 3. 연속하는 숫자/글자 막기 (ex : abc, 123...)
     */
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

