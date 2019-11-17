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
    }
}