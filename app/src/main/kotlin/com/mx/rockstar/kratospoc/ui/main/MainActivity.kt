/*
 * Designed and developed by 2023 Terry1921 (Enrique Espinoza)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mx.rockstar.kratospoc.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import com.mx.rockstar.kratospoc.R
import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.databinding.LayoutMainBinding
import com.skydoves.bindables.BindingActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BindingActivity<LayoutMainBinding>(R.layout.layout_main) {

    @get:VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding {
            vm = viewModel
        }

        lifecycleScope.launch {
            viewModel.listFlow.collect { userInterface ->
                userInterface.nodes.forEach { node ->
                    checkNode(node, userInterface)
                }
            }
        }
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

                FormViewType.PASSWORD,
                FormViewType.TEXT -> {
                    val view = AppCompatEditText(binding.root.context)
                    view.hint = attributes.name
                    view.setText(attributes.value)
                    view.isEnabled = !attributes.disabled
                    binding.container.addView(view)
                }

                FormViewType.SUBMIT -> {
                    val view = AppCompatButton(binding.root.context)
                    view.text = attributes.name
                    view.isEnabled = !attributes.disabled
                    view.setOnClickListener { onClickForm(userInterface) }
                    binding.container.addView(view)
                }
            }
        }
    }

    private fun onClickForm(userInterface: UserInterface) {
        val (identifier: String, password: String) = getFormData()
        if (validateForm(identifier, password)) return
        userInterface.nodes.forEach { node ->
            node.attributes.whatIfNotNull { attributes ->
                when (FormViewType.valueOf(attributes.type.uppercase())) {
                    FormViewType.HIDDEN -> {}
                    FormViewType.TEXT -> attributes.value = identifier
                    FormViewType.PASSWORD -> attributes.value = password
                    FormViewType.SUBMIT -> {}
                }
            }
        }
        viewModel.postForm(userInterface)
        Timber.d("userInterface: action -> $userInterface")
    }

    private fun validateForm(identifier: String, password: String): Boolean {
        if (identifier.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@MainActivity, "please fill the form", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun getFormData(): Pair<String, String> {
        val identifierEt = binding.container.getChildAt(1) as AppCompatEditText
        val passwordEt = binding.container.getChildAt(2) as AppCompatEditText
        val identifier: String = identifierEt.text.toString()
        val password: String = passwordEt.text.toString()
        return Pair(identifier, password)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchLoginForm()
    }
}