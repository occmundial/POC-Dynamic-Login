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
package com.mx.rockstar.kratospoc.core.data.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.mx.rockstar.kratospoc.core.network.AppDispatcher
import com.mx.rockstar.kratospoc.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@VisibleForTesting
class MainRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : MainRepository {

    @WorkerThread
    override fun fetchData(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ): Flow<String> = flow {
        kotlinx.coroutines.delay(1000)
        if (page == 0) {
            emit("Comenzamos...")
        } else if (page <= 10) {
            emit("Flujo #$page Terminando")
        } else {
            onError("Ya son Muchas...")
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}
