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

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import com.mx.rockstar.kratospoc.core.data.kratos.Repository
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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var message: String? by bindingProperty(null)
        set

    private val fetchingIndex: MutableStateFlow<MainState> = MutableStateFlow(MainState.StandBy)

    val listFlow: Flow<UserInterface> = fetchingIndex.flatMapLatest { state ->
        when (state) {
            MainState.Idle -> {
                Timber.d("fetchingIndex: Idle")
                repository.fetchLoginForm(
                    onStart = { isLoading = true },
                    onComplete = {
                        isLoading = false
                        fetchingIndex.value = MainState.StandBy
                    },
                    onError = { message = it }
                )
            }

            MainState.StandBy -> {
                Timber.d("fetchingIndex: StandBy")
                flow { }
            }
        }
    }

    init {
        Timber.d("init MainViewModel")
    }

    @MainThread
    fun fetchLoginForm() {
        if (!isLoading) {
            fetchingIndex.value = MainState.Idle
        }
    }

}

sealed class MainState {
    object Idle : MainState()
    object StandBy : MainState()
}