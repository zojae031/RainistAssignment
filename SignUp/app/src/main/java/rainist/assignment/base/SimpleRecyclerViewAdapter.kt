package rainist.assignment.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleRecyclerViewAdapter<ITEM : Any, B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val bindingVariableId: Int? = null
) : RecyclerView.Adapter<BaseViewHolder<B>>() {

    private val items = mutableListOf<ITEM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> =
        object : BaseViewHolder<B>(
            layoutId,
            parent,
            bindingVariableId
        ) {}

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        holder.bind(items[position])
    }

    @MainThread
    fun updateLists(list: List<ITEM>) {
        items.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

}