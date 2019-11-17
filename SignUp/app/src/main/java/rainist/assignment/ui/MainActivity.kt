package rainist.assignment.ui

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import rainist.assignment.R
import rainist.assignment.base.BaseActivity
import rainist.assignment.databinding.ActivityMainBinding
import rainist.assignment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModel<MainViewModel>()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            vm = viewModel.apply {
                //TODO SOMETHING
            }
        }

    }
}
