package com.mx.rockstar.kratospoc.ui.registration

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.mx.rockstar.kratospoc.R
import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.databinding.LayoutRegistrationBinding
import com.mx.rockstar.kratospoc.ui.login.FormViewType
import com.skydoves.bindables.BindingActivity
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

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
                userInterface.nodes.forEach { checkNode(userInterface, it) }
            }
        }
        lifecycleScope.launch {
            viewModel.submitFlow.collect {
                binding.response.text = it.toString()
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

    private fun checkNode(userInterface: UserInterface, node: Node) {
        node.attributes.whatIfNotNull { attributes ->
            when (FormViewType.valueOf(attributes.type.uppercase())) {
                FormViewType.HIDDEN -> {
                    val view = AppCompatEditText(binding.root.context)
                    view.hint = attributes.name
                    view.setText(attributes.value)
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
                    view.hint = attributes.value
                    view.isEnabled = !attributes.disabled
                    view.setOnClickListener { onClickForm(userInterface) }
                    binding.container.addView(view)
                }
            }
        }
    }

    private fun onClickForm(userInterface: UserInterface) {
        val form = binding.container
        val formValues = JSONObject()
        for (i in 0 until form.childCount) {
            val view = form.getChildAt(i)
            if (view is AppCompatEditText) {
                formValues.put(view.hint.toString(), view.text.toString())
            }
            if (view is AppCompatButton) {
                formValues.put(view.text.toString(), view.hint.toString())
            }
        }
        Timber.d("formValues: $formValues")
        viewModel.submitForm(action = userInterface.action, form = formValues.toString())

    }

}