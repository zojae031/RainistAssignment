package rainist.assignment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rainist.assignment.R
import rainist.assignment.base.BaseFragmentDialog
import rainist.assignment.databinding.DialogMainBinding
import rainist.assignment.viewmodel.MainViewModel

class MainDialog : BaseFragmentDialog<DialogMainBinding>() {
    private val viewModel by sharedViewModel<MainViewModel>()
    override val layoutId: Int = R.layout.dialog_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            vm = viewModel.apply {
                allPermission.observe(this@MainDialog, Observer {
                    checkAllPermission(it)
                })
            }
        }
    }
}