package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.dao.UserEntitiy
import rainist.assignment.util.ValidationUtil

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




    fun checkNameValidation(name: String) {
        if (ValidationUtil.checkName(name)) {
            _nameState.value = true
            _nameStateText.value = NAME_SUCCESS
        } else {
            _nameState.value = false
            _nameStateText.value = NAME_ERR
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

    fun checkEmailValidation(email: String) {
        if (ValidationUtil.checkEmail(email)) {
            _emailStateText.value = EMAIL_SUCCESS
            _emailState.value = true
        } else {
            _emailStateText.value = EMAIL_ERR
            _emailState.value = false
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
    }
}