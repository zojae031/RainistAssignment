package rainist.assignment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rainist.assignment.R
import rainist.assignment.base.BaseActivity
import rainist.assignment.databinding.ActivityMainBinding
import rainist.assignment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    private val viewModel by viewModel<MainViewModel>()
    private val dialog = MainDialog()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            vm = viewModel.apply {

                getUserData()

                email_edit.addTextChangedListener {
                    checkEmailValidation(it.toString())
                }
                password.addTextChangedListener {
                    checkPasswordValidation(password.text.toString(), password2.text.toString())
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
                    if (!it) {
                        Toast.makeText(applicationContext, "회원 정보를 입력하세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                message.observe(this@MainActivity, Observer {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                })
                error.observe(this@MainActivity, Observer {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                })
            }
        }
    }

    override fun onClick(v: View?) {
        dialog.show(supportFragmentManager, "MainDialog")
    }

    override fun onStop() {
        viewModel.clearDisposable()
        super.onStop()
    }
}
