package rainist.assignment.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VIEW_DATA_BINDING : ViewDataBinding> :
    AppCompatActivity() {
    abstract val layoutId: Int
    protected lateinit var binding: VIEW_DATA_BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }
}