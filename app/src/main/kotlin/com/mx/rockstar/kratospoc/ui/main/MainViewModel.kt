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

import android.net.Uri
import androidx.annotation.MainThread
import androidx.databinding.Bindable
import com.mx.rockstar.kratospoc.core.data.kratos.Repository
import com.mx.rockstar.kratospoc.core.model.kratos.Form
import com.mx.rockstar.kratospoc.core.model.kratos.SessionResponse
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * MainViewModel is a bridge between [MainActivity] and [Repository].
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var message: String? by bindingProperty(null)

    private val fetchingForm: MutableStateFlow<MainState> = MutableStateFlow(MainState.StandBy)
    val listFlow: Flow<UserInterface> = fetchingForm.flatMapLatest { state ->
        when (state) {
            is MainState.Fetch -> {
                Timber.d("fetchingForm: Fetch ${state.status}")
                repository.fetchLoginForm(
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        fetchingForm.value = MainState.StandBy
                    },
                    onError = { message = it }
                )
            }

            MainState.StandBy -> {
                Timber.d("fetchingForm: StandBy")
                flow { }
            }
        }
    }

    private val settingForm: MutableStateFlow<MainSubmit> = MutableStateFlow(MainSubmit.StandBy)
    val formFlow: Flow<SessionResponse> = settingForm.flatMapLatest { submit ->
        when (submit) {
            is MainSubmit.Submit -> {
                Timber.d("settingForm: Submit ${submit.form}")
                repository.postForm(
                    action = getFlowId(submit.action),
                    form = submit.form,
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        settingForm.value = MainSubmit.StandBy
                    },
                    onError = { message = it }
                )
            }

            MainSubmit.StandBy -> {
                Timber.d("settingForm: StandBy")
                flow { }
            }
        }
    }

    private fun getFlowId(action: String): String =
        Uri.parse(action).getQueryParameter("flow").toString()


    /**
     * fetchLoginForm is an action for fetching the login form.
     */
    @MainThread
    fun fetchLoginForm(status: String) {
        if (!isLoading) {
            fetchingForm.value = MainState.Fetch(status)
        }
    }

    /**
     * postForm is an action for posting the form.
     */
    @MainThread
    fun postForm(action: String, form: Form) {
        if (!isLoading) {
            message = null
            settingForm.value = MainSubmit.Submit(action, form)
        }
    }

}

sealed class MainState {
    data class Fetch(val status: String) : MainState()
    object StandBy : MainState()
}

sealed class MainSubmit {
    data class Submit(val action: String, val form: Form) : MainSubmit()
    object StandBy : MainSubmit()
}