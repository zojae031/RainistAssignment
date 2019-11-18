package rainist.assignment.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rainist.assignment.R
import rainist.assignment.base.BaseActivity
import rainist.assignment.databinding.ActivityMainBinding
import rainist.assignment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModel<MainViewModel>()
    private val dialog = MainDialog()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            vm = viewModel.apply {
                email_edit.addTextChangedListener {
                    checkEmailValidation(it.toString())
                }
                password2.addTextChangedListener {
                    checkPasswordValidation(password.text.toString(), password2.text.toString())
                }
                name.addTextChangedListener {
                    checkNameValidation(it.toString())
                }
                identify.addTextChangedListener {
                    checkIdentifyValidation(it.toString())
                    identify.setSelection(identify.length())
                }
                signUpState.observe(this@MainActivity, Observer {
                    if (it) {
                        Toast.makeText(applicationContext, "앙 시작띠", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "회원 정보를 입력하세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                activity = this@MainActivity
            }
        }
    }

    fun showDialog() {
        dialog.show(supportFragmentManager, "MainDialog")
    }

    override fun onStop() {
        viewModel.clearDisposable()
        super.onStop()
    }
}
