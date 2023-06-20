package com.mx.rockstar.kratospoc.core.data.kratos

import androidx.annotation.WorkerThread
import com.mx.rockstar.kratospoc.core.model.kratos.LoginForm
import com.mx.rockstar.kratospoc.core.model.kratos.RegistrationForm
import com.mx.rockstar.kratospoc.core.model.kratos.SessionResponse
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import kotlinx.coroutines.flow.Flow

/**
 * The `Repository` interface defines methods for fetching login and registration forms from the
 * network.
 */
interface Repository {

    /**
     * Fetches the login form from the network.
     */
    @WorkerThread
    fun fetchLoginForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ): Flow<UserInterface>

    /**
     * Fetches the registration form from the network.
     */
    @WorkerThread
    fun fetchRegistrationForm(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ): Flow<UserInterface>

    @WorkerThread
    fun postLoginForm(
        action: String,
        form: LoginForm,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<SessionResponse>

    @WorkerThread
    fun postRegistrationForm(
        action: String,
        form: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<SessionResponse>

}