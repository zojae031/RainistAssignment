package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.UserEntitiy
import rainist.assignment.util.ValidationUtil

class MainViewModel : BaseViewModel() {

    private val _user = MutableLiveData<UserEntitiy>()
    val user: LiveData<UserEntitiy>
        get() = _user

    private val _emailText = MutableLiveData<String>()
    val emailText: LiveData<String>
        get() = _emailText

    private val _emailState = MutableLiveData<Boolean>()
    val emailState: LiveData<Boolean>
        get() = _emailState

    private val _passwordState = MutableLiveData<Boolean>()
    val passwordState: LiveData<Boolean>
        get() = _passwordState

    private val _passwordText = MutableLiveData<String>()
    val passwordText: LiveData<String>
        get() = _passwordText

    fun checkPasswordValidation(pw1: String, pw2: String) {
        if (ValidationUtil.checkPassword(pw1, pw2)) {
            _passwordState.value = true
            _passwordText.value = PASSWORD_SUCCESS
        } else {
            _passwordState.value = false
            _passwordText.value = PASSWORD_ERR
        }
    }

    fun checkEmailValidation(email: String) {
        if (ValidationUtil.checkEmail(email)) {
            _emailText.value = EMAIL_SUCCESS
            _emailState.value = true
        } else {
            _emailText.value = EMAIL_ERR
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
    }
}