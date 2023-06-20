package com.mx.rockstar.kratospoc.ui.registration

import android.net.Uri
import androidx.databinding.Bindable
import com.mx.rockstar.kratospoc.core.data.kratos.Repository
import com.mx.rockstar.kratospoc.core.model.kratos.SessionResponse
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.ui.login.FormState
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: Repository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var message: String? by bindingProperty(null)

    private val fetchingForm: MutableStateFlow<FormState> = MutableStateFlow(FormState.StandBy)
    val formFlow: Flow<UserInterface> = fetchingForm.flatMapLatest { state ->
        when (state) {
            is FormState.Fetch -> {
                Timber.d("fetchingForm: Fetch ${state.status}")
                repository.fetchRegistrationForm(
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        fetchingForm.value = FormState.StandBy
                    },
                    onError = { message = it }
                )
            }

            FormState.StandBy -> {
                flow { }
            }
        }
    }

    private val settingForm: MutableStateFlow<RegistrationSubmit> = MutableStateFlow(RegistrationSubmit.StandBy)
    val submitFlow: Flow<SessionResponse> = settingForm.flatMapLatest { submit ->
        when (submit) {
            is RegistrationSubmit.SubmitRegistration -> {
                Timber.d("settingForm: Submit ${submit.form}")
                repository.postRegistrationForm(
                    action = getFlowId(submit.action),
                    form = submit.form,
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        settingForm.value = RegistrationSubmit.StandBy
                    },
                    onError = { message = it },
                )
            }

            RegistrationSubmit.StandBy -> {
                Timber.d("settingForm: StandBy")
                flow { }
            }
        }
    }

    private fun getFlowId(action: String): String =
        Uri.parse(action).getQueryParameter("flow").toString()

    fun fetchRegistrationForm(status: String) {
        if (!isLoading) {
            fetchingForm.value = FormState.Fetch(status)
        }
    }

    fun submitForm(action: String, form: String) {
        if (!isLoading) {
            message = null
            settingForm.value = RegistrationSubmit.SubmitRegistration(action, form)
        }
    }

}

sealed class RegistrationSubmit {
    data class SubmitRegistration(val action: String, val form: String) : RegistrationSubmit()
    object StandBy : RegistrationSubmit()
}