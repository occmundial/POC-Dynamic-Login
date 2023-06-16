package com.mx.rockstar.kratospoc.ui.registration

import androidx.databinding.Bindable
import com.mx.rockstar.kratospoc.core.data.kratos.Repository
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.ui.login.MainState
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

    private val fetchingForm: MutableStateFlow<MainState> = MutableStateFlow(MainState.StandBy)
    val formFlow: Flow<UserInterface> = fetchingForm.flatMapLatest { state ->
        when (state) {
            is MainState.Fetch -> {
                Timber.d("fetchingForm: Fetch ${state.status}")
                repository.fetchRegistrationForm(
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        fetchingForm.value = MainState.StandBy
                    },
                    onError = { message = it }
                )
            }

            MainState.StandBy -> {
                flow { }
            }
        }
    }

    fun fetchRegistrationForm(status: String) {
        if (!isLoading) {
            fetchingForm.value = MainState.Fetch(status)
        }
    }

}