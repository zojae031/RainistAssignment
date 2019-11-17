package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.dao.UserEntitiy
import rainist.assignment.util.ValidationUtil
import rainist.assignment.util.ValidationUtil.IdentifyState.*

class MainViewModel : BaseViewModel() {

    private val _user = MutableLiveData<UserEntitiy>()
    val user: LiveData<UserEntitiy>
        get() = _user

    //Email
    val emailText = MutableLiveData<String>()

    private val _emailStateText = MutableLiveData<String>()
    val emailStateText: LiveData<String>
        get() = _emailStateText

    private val _emailState = MutableLiveData<Boolean>()
    val emailState: LiveData<Boolean>
        get() = _emailState

    //Password
    val passwordText = MutableLiveData<String>()

    private val _passwordState = MutableLiveData<Boolean>()
    val passwordState: LiveData<Boolean>
        get() = _passwordState

    private val _passwordStateText = MutableLiveData<String>()
    val passwordStateText: LiveData<String>
        get() = _passwordStateText

    //Name
    val nameText = MutableLiveData<String>()

    private val _nameState = MutableLiveData<Boolean>()
    val nameState: LiveData<Boolean>
        get() = _nameState

    private val _nameStateText = MutableLiveData<String>()
    val nameStateText: LiveData<String>
        get() = _nameStateText

    //Identify
    private val _identifyText = MutableLiveData<String>("")
    val identifyText: LiveData<String>
        get() = _identifyText

    private val _identifyStateText = MutableLiveData<String>()
    val identifyStateText: LiveData<String>
        get() = _identifyStateText

    private val _identifyState = MutableLiveData<Boolean>()
    val identifyState: LiveData<Boolean>
        get() = _identifyState

    //sex
    val sex = MutableLiveData<ValidationUtil.IdentifyState>(ERROR)

    fun checkEmailValidation(email: String) {
        if (ValidationUtil.checkEmail(email)) {
            _emailStateText.value = EMAIL_SUCCESS
            _emailState.value = true
        } else {
            _emailStateText.value = EMAIL_ERR
            _emailState.value = false
        }
    }

    fun checkPasswordValidation(pw1: String, pw2: String) {
        if (ValidationUtil.checkPassword(pw1, pw2)) {
            _passwordState.value = true
            _passwordStateText.value = PASSWORD_SUCCESS
        } else {
            _passwordState.value = false
            _passwordStateText.value = PASSWORD_ERR
        }
    }

    fun checkNameValidation(name: String) {
        if (ValidationUtil.checkName(name)) {
            _nameState.value = true
            _nameStateText.value = NAME_SUCCESS
        } else {
            _nameState.value = false
            _nameStateText.value = NAME_ERR
        }
    }

    fun checkIdentifyValidation(identify: String) {
        if (ValidationUtil.checkIdentifyFirst(identify)) {
            _identifyText.value += "$identify-"
        } else {
            when (ValidationUtil.checkIdentifySex(identify.substringAfterLast('-'))) {
                MALE -> sex.value =
                    MALE
                FEMALE -> sex.value =
                    FEMALE
                ERROR -> sex.value =
                    ERROR
            }
            _identifyStateText.value = IDENTIFY_ERR
            _identifyState.value = false
        }

        if (sex.value != ERROR && ValidationUtil.checkIdentifyLast(
                identify.substringAfterLast(
                    '-'
                )
            )
        ) {
            _identifyStateText.value = IDENTIFY_SUCCESS
            _identifyState.value = true
        }
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        val EMAIL_ERR = "이메일이 올바르지 않습니다."
        @JvmStatic
        val EMAIL_SUCCESS = "올바른 이메일 입니다."
        @JvmStatic
        val PASSWORD_SUCCESS = "암호가 올바릅니다."
        @JvmStatic
        val PASSWORD_ERR = "암호가 올바르지 않습니다."
        @JvmStatic
        val NAME_SUCCESS = "올바른 이름 입니다."
        @JvmStatic
        val NAME_ERR = "이름은 10글자를 넘을 수 없습니다."
        @JvmStatic
        val IDENTIFY_SUCCESS = "올바른 주민번호입니다."
        @JvmStatic
        val IDENTIFY_ERR = "잘못된 주민번호입니다."
    }
}