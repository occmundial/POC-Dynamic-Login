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

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.mx.rockstar.kratospoc.core.data.repository.MainRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var message: String? by bindingProperty(null)
        private set

    private val fetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private val listFlow = fetchingIndex.flatMapLatest { page ->
        mainRepository.fetchData(
            page = page,
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { message = it }
        )
    }

    @get:Bindable
    val response: String by listFlow.asBindingProperty(viewModelScope, "")

    init {
        Timber.d("init MainViewModel")
    }

    fun updateMessage() {
        if (!isLoading) {
            message = ""
            fetchingIndex.value++
        }
    }

}