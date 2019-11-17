package rainist.assignment.ui

import android.os.Bundle
import rainist.assignment.R
import rainist.assignment.base.BaseFragmentDialog
import rainist.assignment.databinding.DialogMainBinding

class MainDialog() : BaseFragmentDialog<DialogMainBinding>() {

    override val layoutId: Int = R.layout.dialog_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}