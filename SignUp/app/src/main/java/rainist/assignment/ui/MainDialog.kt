package rainist.assignment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                permissionState.observe(this@MainDialog, Observer {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "필수 약관에 동의해야 합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}