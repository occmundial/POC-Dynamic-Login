package com.mx.rockstar.kratospoc.core.data.kratos

import androidx.annotation.VisibleForTesting
import com.mx.rockstar.kratospoc.core.model.kratos.Form
import com.mx.rockstar.kratospoc.core.model.kratos.SessionResponse
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.mx.rockstar.kratospoc.core.network.AppDispatcher
import com.mx.rockstar.kratospoc.core.network.Dispatcher
import com.mx.rockstar.kratospoc.core.network.service.KratosClient
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@VisibleForTesting
class KratosRepository @Inject constructor(
    private val kratosClient: KratosClient,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : Repository {

    override fun fetchLoginForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<UserInterface> = flow {
        val response = kratosClient.getLoginForm()
        response.suspendOnSuccess {
            val userInterface = data.ui
            Timber.d("Request: onSuccess -> $data")
            emit(userInterface)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun fetchRegistrationForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<UserInterface> = flow {
        val response = kratosClient.getRegistrationForm()
        response.suspendOnSuccess {
            val userInterface = data.ui
            emit(userInterface)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun postForm(
        action: String,
        form: Form,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<SessionResponse> = flow {
        Timber.d("postForm -> action: $action form: $form")
        val response = kratosClient.postForm(action, form)
        response.suspendOnSuccess {
            Timber.d("Request: onSuccess -> $data")
            emit(data)
        }.onFailure {
            Timber.d("Request: onFailure-> ${message()}")
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}