package rainist.assignment.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<B : ViewDataBinding>(
    @LayoutRes layoutId: Int,
    parents: ViewGroup,
    private val bindingVariableId: Int? = null
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parents.context)
            .inflate(layoutId, parents, false)
    ) {

    private val binding: B = DataBindingUtil.bind(itemView) ?: throw Exception("Binding is null")

    fun bind(item: Any?) {
        binding.run {
            bindingVariableId?.let {
                setVariable(it, item)
            }
            executePendingBindings()
        }
    }
}