package com.mx.rockstar.kratospoc.core.data.kratos

import app.cash.turbine.test
import com.mx.rockstar.kratospoc.core.network.model.KratosResponse
import com.mx.rockstar.kratospoc.core.network.service.KratosClient
import com.mx.rockstar.kratospoc.core.network.service.KratosService
import com.mx.rockstar.kratospoc.core.test.MainCoroutinesRule
import com.mx.rockstar.kratospoc.core.test.MockUtil.mockUserInterface
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class KratosRepositoryTest {

    private lateinit var repository: KratosRepository
    private lateinit var client: KratosClient
    private val service: KratosService = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        client = KratosClient(service)
        repository = KratosRepository(client, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchLoginFormTest() = runTest {
        val mockData = KratosResponse(
            id = "cdf6a844",
            expiresAt = "2023-05-24T00:40:31.107603817Z",
            issuedAt = "2023-05-23T23:40:31.107603817Z",
            requestUrl = "https://www.occdev.com.mx/self-service/login/api",
            refresh = false,
            ui = mockUserInterface()
        )
        whenever(service.getLoginForm()).thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchLoginForm(
            onStart = {},
            onComplete = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS) * 2) {
            val expectItem = awaitItem()
            assertEquals(expectItem.method, mockData.ui.method)
            assertEquals(expectItem.action, mockData.ui.action)
            assertEquals(expectItem.nodes.size, mockData.ui.nodes.size)
            awaitComplete()
        }

    }

}