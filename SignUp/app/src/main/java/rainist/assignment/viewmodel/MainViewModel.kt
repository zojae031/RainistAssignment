package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import io.reactivex.android.schedulers.AndroidSchedulers
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.Repository
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.data.datasource.remote.Http401Exception
import rainist.assignment.data.datasource.remote.Http404Exception
import rainist.assignment.util.ConstUtil.EMAIL_ERR
import rainist.assignment.util.ConstUtil.EMAIL_SUCCESS
import rainist.assignment.util.ConstUtil.ERROR_401
import rainist.assignment.util.ConstUtil.ERROR_404
import rainist.assignment.util.ConstUtil.IDENTIFY_ERR
import rainist.assignment.util.ConstUtil.IDENTIFY_SUCCESS
import rainist.assignment.util.ConstUtil.NAME_ERR
import rainist.assignment.util.ConstUtil.NAME_SUCCESS
import rainist.assignment.util.ConstUtil.PASSWORD_ERR
import rainist.assignment.util.ConstUtil.PASSWORD_SUCCESS
import rainist.assignment.util.SingleLiveEvent
import rainist.assignment.util.ValidationUtil
import rainist.assignment.util.ValidationUtil.IdentifyState.*
import timber.log.Timber

class MainViewModel(private val repository: Repository) : BaseViewModel() {
    private var tempIdData = 0
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

    fun getUserData() {
        repository.getUserInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                var index = 0
                emailText.value = data.email
                passwordText.value = data.password
                nameText.value = data.name
                _identifyText.value = data.pId
                permissionList.map {
                    it.value = data.permission.get(index++).asBoolean
                }
            }.also { compositeDisposable.add(it) }
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
        permissionList.map { it.value = check }
    }

    fun checkPermissionValidation() {
        _permissionState.value = (permissionList[1].value!! && permissionList[2].value!!)
    }

    fun checkSignUpValidation() {
        if (_emailState.value!! && _passwordState.value!! && _nameState.value!! && _identifyState.value!! && _permissionState.value == true) {

            _signUpState.value = true

            UserEntity(
                tempIdData++,
                emailText.value.toString(),
                passwordText.value.toString(),
                nameText.value.toString(),
                identifyText.value.toString(),
                sex.value!!.ordinal,
                JsonArray().apply {
                    permissionList.map { isCheck ->
                        add(isCheck.value!!)
                    }
                }
            ).run {
                repository.requestSignUp(this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _loadingState.value = true }
                    .doOnSuccess { _loadingState.value = false }
                    .doOnError { _loadingState.value = false }
                    .subscribe(
                        { data ->
                            _message.value = data
                        },
                        { error ->
                            when (error) {
                                is Http401Exception -> _error.value = ERROR_401
                                is Http404Exception -> _error.value = ERROR_404
                            }
                            Timber.e(error)
                        })
                    .also { compositeDisposable.add(it) }
            }

        } else _signUpState.value = false
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

}