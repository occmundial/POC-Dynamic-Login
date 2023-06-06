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
package com.mx.rockstar.kratospoc.core.network.service

import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.core.network.model.KratosResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface KratosService {

    @GET("kratos/self-service/login/api")
    suspend fun getLoginForm(): ApiResponse<KratosResponse>

    @GET("kratos/self-service/registration/api")
    suspend fun getRegistrationForm(): ApiResponse<KratosResponse>

    @POST("kratos/self-service/login")
    suspend fun postForm(
        @Query("flow") action: String,
        @Body nodes: List<Node>
    ): ApiResponse<String>

    @Multipart
    @POST("kratos/self-service/login")
    suspend fun postForm(
        @Query("flow") action: String,
        @Part("csrf_token") token: String,
        @Part("identifier") identifier: String,
        @Part("password") password: String
    ): ApiResponse<String>

    @FormUrlEncoded
    @POST("kratos/self-service/login")
    suspend fun postFormEncoded(
        @Query("flow") action: String,
        @Field("csrf_token") token: String,
        @Field("identifier") identifier: String,
        @Field("password") password: String
    ): ApiResponse<String>

}
