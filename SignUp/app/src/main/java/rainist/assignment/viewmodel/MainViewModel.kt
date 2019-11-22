package rainist.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import rainist.assignment.R
import rainist.assignment.base.BaseViewModel
import rainist.assignment.data.Repository
import rainist.assignment.data.dao.UserEntity
import rainist.assignment.data.datasource.remote.Http401Exception
import rainist.assignment.data.datasource.remote.Http404Exception
import rainist.assignment.util.ResourceUtil
import rainist.assignment.util.SingleLiveEvent
import rainist.assignment.util.ValidationUtil
import rainist.assignment.util.ValidationUtil.IdentifyState.*
import timber.log.Timber

class MainViewModel(private val repository: Repository, private val resourceUtil: ResourceUtil) :
    BaseViewModel() {
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

    //permission
    val permissionList = arrayOf(
        MutableLiveData(false),
        MutableLiveData(false),
        MutableLiveData(false),
        MutableLiveData(false)
    )

    private val _permissionState = SingleLiveEvent<Boolean>()
    val permissionState: LiveData<Boolean>
        get() = _permissionState

    private val _permissionColorState = MutableLiveData<Boolean>(false)
    val permissionColorState: LiveData<Boolean>
        get() = _permissionColorState

    //SignUp
    private val _signUpState = MutableLiveData(false)
    val signUpState: LiveData<Boolean>
        get() = _signUpState

    //Ext
    private val _finishState = MutableLiveData<Boolean>()
    val finishState: LiveData<Boolean>
        get() = _finishState

    private val backPressSubject =
        BehaviorSubject.createDefault(0L)

    init {
        backPressSubject
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe(
                {
                    _finishState.value = it.second - it.first < TOAST_DURATION
                },
                {
                    _error.value = it.message
                }
            ).also { compositeDisposable.add(it) }
    }


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
            _emailState.value = true
            _emailStateText.value = resourceUtil.getResource(R.string.email_success)
        } else {
            _emailState.value = false
            _emailStateText.value = resourceUtil.getResource(R.string.email_err)
        }
    }

    fun checkPasswordValidation(pw1: String, pw2: String) {
        if (pw1.isEmpty() && pw2.isEmpty()) _passwordState.value = false
        if (ValidationUtil.checkPassword(pw1, pw2)) {
            _passwordState.value = true
            _passwordStateText.value = resourceUtil.getResource(R.string.pw_success)
        } else {
            _passwordState.value = false
            _passwordStateText.value = resourceUtil.getResource(R.string.pw_err)
        }
    }

    fun checkNameValidation(name: String) {
        if (ValidationUtil.checkName(name)) {
            _nameState.value = true
            _nameStateText.value = resourceUtil.getResource(R.string.name_success)
        } else {
            _nameState.value = false
            _nameStateText.value = resourceUtil.getResource(R.string.name_err)
        }
    }

    fun checkIdentifyValidation(identify: String) {
        _identifyText.value = identify
        if (ValidationUtil.checkIdentify(identify)) {
            _identifyState.value = true
            _identifyStateText.value = resourceUtil.getResource(R.string.id_success)
        } else {
            _identifyState.value = false
            _identifyStateText.value = resourceUtil.getResource(R.string.id_err)
        }
        if (identify.split('-').size > 1) {
            when (ValidationUtil.checkIdentifySex(identify.substringAfterLast('-'))) {
                MALE -> _sex.value =
                    MALE
                FEMALE -> _sex.value =
                    FEMALE
                ERROR -> _sex.value =
                    ERROR
            }
        }
    }

    fun checkAllPermission(check: Boolean) {
        permissionList.map { it.value = check }
    }

    fun checkPermissionValidation() {
        _permissionState.value = (permissionList[1].value!! && permissionList[2].value!!)
        _permissionColorState.value = (permissionList[1].value!! && permissionList[2].value!!)
    }

    fun checkSignUpValidation() {
        if (_emailState.value!! && _passwordState.value!! && _nameState.value!! && _identifyState.value!! && _permissionState.value == true) {

            _signUpState.value = true
            _permissionColorState.value = (permissionList[1].value!! && permissionList[2].value!!)

            UserEntity(
                AUTO_INCREMENT_DATA,
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
                requestSignUp(this)
            }

        } else _signUpState.value = false
    }

    private fun requestSignUp(entity: UserEntity) {
        repository.requestSignUp(entity)
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
                        is Http401Exception -> _error.value =
                            resourceUtil.getResource(R.string.error_401)
                        is Http404Exception -> _error.value =
                            resourceUtil.getResource(R.string.error_404)
                    }
                    Timber.e(error)
                })
            .also { compositeDisposable.add(it) }
    }

    fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    companion object {
        const val TOAST_DURATION = 1000L
        const val AUTO_INCREMENT_DATA = 0
    }
}