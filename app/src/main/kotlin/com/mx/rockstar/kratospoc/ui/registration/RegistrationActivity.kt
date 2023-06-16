package com.mx.rockstar.kratospoc.ui.registration

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import com.mx.rockstar.kratospoc.R
import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.databinding.LayoutRegistrationBinding
import com.mx.rockstar.kratospoc.ui.login.FormViewType
import com.skydoves.bindables.BindingActivity
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationActivity :
    BindingActivity<LayoutRegistrationBinding>(R.layout.layout_registration) {

    @get:VisibleForTesting
    internal val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            vm = viewModel
        }

        binding.fab.setOnClickListener {
            fetchRegistrationForm("update form")
        }

        lifecycleScope.launch {
            viewModel.formFlow.collect { userInterface ->
                userInterface.nodes.forEach { node ->
                    checkNode(node, userInterface)
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        fetchRegistrationForm("fetch form")
    }

    private fun fetchRegistrationForm(status: String) {
        binding.container.removeAllViews()
        viewModel.fetchRegistrationForm(status)
    }

    private fun checkNode(node: Node, userInterface: UserInterface) {
        node.attributes.whatIfNotNull { attributes ->
            when (FormViewType.valueOf(attributes.type.uppercase())) {
                FormViewType.HIDDEN -> {
                    val view = AppCompatTextView(binding.root.context)
                    view.text = attributes.value
                    view.visibility = View.GONE
                    binding.container.addView(view)
                }

                FormViewType.TEXT -> {
                    val view = AppCompatEditText(binding.root.context)
                    view.hint = attributes.name
                    view.isEnabled = !attributes.disabled
                    binding.container.addView(view)
                }

                FormViewType.PASSWORD -> {
                    val view = AppCompatEditText(binding.root.context)
                    view.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    view.hint = attributes.name
                    view.isEnabled = !attributes.disabled
                    binding.container.addView(view)
                }

                FormViewType.SUBMIT -> {
                    val view = AppCompatButton(binding.root.context)
                    view.text = attributes.name
                    view.isEnabled = !attributes.disabled
                    //view.setOnClickListener { onClickForm(userInterface) }
                    binding.container.addView(view)
                }
            }
        }
    }

}