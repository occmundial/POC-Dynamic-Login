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
package com.mx.rockstar.kratospoc.core.network.model

import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KratosResponse(
  @field:Json(name = "id") val id: String,
  @field:Json(name = "expires_at") val expiresAt: String,
  @field:Json(name = "issued_at") val issuedAt: String,
  @field:Json(name = "request_url") val requestUrl: String,
  @field:Json(name = "refresh") val refresh: Boolean = false,
  @field:Json(name = "ui") val ui: UserInterface,
)
