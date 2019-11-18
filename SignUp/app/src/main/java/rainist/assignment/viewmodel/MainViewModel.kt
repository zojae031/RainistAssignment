package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.util.SingleLiveEvent
import rainist.assignment.util.ValidationUtil
import rainist.assignment.util.ValidationUtil.IdentifyState.*
import timber.log.Timber

class MainViewModel : BaseViewModel() {
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user

    //Email
    val emailText = MutableLiveData<String>("")

    private val _emailStateText = MutableLiveData<String>("")
    val emailStateText: LiveData<String>
        get() = _emailStateText

    private val _emailState = MutableLiveData<Boolean>(false)
    val emailState: LiveData<Boolean>
        get() = _emailState

    //Password
    val passwordText = MutableLiveData<String>("")

    private val _passwordState = MutableLiveData<Boolean>(false)
    val passwordState: LiveData<Boolean>
        get() = _passwordState

    private val _passwordStateText = MutableLiveData<String>("")
    val passwordStateText: LiveData<String>
        get() = _passwordStateText

    //Name
    val nameText = MutableLiveData<String>("")

    private val _nameState = MutableLiveData<Boolean>(false)
    val nameState: LiveData<Boolean>
        get() = _nameState

    private val _nameStateText = MutableLiveData<String>("")
    val nameStateText: LiveData<String>
        get() = _nameStateText

    //Identify
    private val _identifyText = MutableLiveData<String>("")
    val identifyText: LiveData<String>
        get() = _identifyText

    private val _identifyStateText = MutableLiveData<String>("")
    val identifyStateText: LiveData<String>
        get() = _identifyStateText

    private val _identifyState = MutableLiveData<Boolean>(false)
    val identifyState: LiveData<Boolean>
        get() = _identifyState

    //sex
    private val _sex = MutableLiveData<ValidationUtil.IdentifyState>(ERROR)
    val sex: LiveData<ValidationUtil.IdentifyState>
        get() = _sex

    //permissionList
    val permissionList = arrayOf(
        MutableLiveData(false),
        MutableLiveData(false),
        MutableLiveData(false),
        MutableLiveData(false)
    )

    private val _permissionState = SingleLiveEvent<Boolean>()
    val permissionState: LiveData<Boolean>
        get() = _permissionState

    //SignUp
    private val _signUpState = MutableLiveData(false)
    val signUpState: LiveData<Boolean>
        get() = _signUpState

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
        if (pw1.isEmpty() && pw2.isEmpty()) _passwordState.value = false
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
        _identifyText.value = identify
        if (ValidationUtil.checkIdentify(identify)) {
            _identifyStateText.value = IDENTIFY_SUCCESS
            _identifyState.value = true
        } else {
            _identifyStateText.value = IDENTIFY_ERR
            _identifyState.value = false
        }
        when (ValidationUtil.checkIdentifySex(identify.substringAfterLast('-'))) {
            MALE -> _sex.value =
                MALE
            FEMALE -> _sex.value =
                FEMALE
            ERROR -> _sex.value =
                ERROR
        }
    }

    fun checkAllPermission(check: Boolean) {
        permissionList.map { it.value = check }.also { Timber.d("호출되나?$it") }
    }

    fun checkPermissionValidation() {
        _permissionState.value = (permissionList[1].value!! && permissionList[2].value!!)
    }

    fun checkSignUpValidation() {
        _signUpState.value =
            _emailState.value == true && _passwordState.value == true && _nameState.value == true && _identifyState.value == true && _permissionState.value == true


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