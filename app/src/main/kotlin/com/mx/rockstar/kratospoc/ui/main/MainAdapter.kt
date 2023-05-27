package com.mx.rockstar.kratospoc.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mx.rockstar.kratospoc.R
import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.databinding.ItemNodeBinding
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding
import com.skydoves.whatif.whatIfNotNull
import timber.log.Timber

class MainAdapter(
    private val listener: () -> Unit
) : BindingListAdapter<Node, MainAdapter.MainViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bindNode(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        parent.binding<ItemNodeBinding>(R.layout.item_node).let(::MainViewHolder)

    inner class MainViewHolder constructor(
        private val binding: ItemNodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindNode(node: Node) {
            Timber.d("================================")
            Timber.d("node: type -> ${node.type}")
            Timber.d("node: group -> ${node.group}")
            node.attributes.whatIfNotNull { attributes ->
                when (FormViewType.valueOf(attributes.type.uppercase())) {
                    FormViewType.HIDDEN -> {
                        binding.type = "hidden"
                        val view = AppCompatTextView(binding.root.context)
                        view.text = attributes.value
                        view.visibility = View.GONE
                        binding.container.addView(view)
                    }

                    FormViewType.TEXT -> {
                        binding.type = "text"
                        val view = AppCompatEditText(binding.root.context)
                        view.hint = attributes.name
                        view.setText(attributes.value)
                        view.isEnabled = !attributes.disabled
                        binding.container.addView(view)
                    }

                    FormViewType.PASSWORD -> {
                        binding.type = "password"
                        val view = AppCompatEditText(binding.root.context)
                        view.hint = attributes.name
                        view.setText(attributes.value)
                        view.isEnabled = !attributes.disabled
                        binding.container.addView(view)
                    }

                    FormViewType.SUBMIT -> {
                        binding.type = "submit"
                        val view = AppCompatButton(binding.root.context)
                        view.text = attributes.name
                        view.isEnabled = !attributes.disabled
                        view.setOnClickListener { listener.invoke() }
                        binding.container.addView(view)
                    }
                }
                Timber.d("attributes: name -> ${attributes.name}")
                Timber.d("attributes: type -> ${attributes.type}")
                Timber.d("attributes: value -> ${attributes.value}")
                Timber.d("attributes: required -> ${attributes.required}")
                Timber.d("attributes: autocomplete -> ${attributes.autocomplete}")
                Timber.d("attributes: disabled -> ${attributes.disabled}")
                Timber.d("attributes: nodeType -> ${attributes.nodeType}")
            }
            node.meta.whatIfNotNull { meta ->
                Timber.d("meta: label -> ${meta.label}")
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Node>() {

            override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean =
                oldItem.attributes == newItem.attributes

            override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean =
                oldItem == newItem
        }
    }

}

enum class FormViewType() {
    HIDDEN, TEXT, PASSWORD, SUBMIT
}