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
package com.mx.rockstar.kratospoc.core.test

import com.mx.rockstar.kratospoc.core.model.kratos.Attributes
import com.mx.rockstar.kratospoc.core.model.kratos.Label
import com.mx.rockstar.kratospoc.core.model.kratos.Meta
import com.mx.rockstar.kratospoc.core.model.kratos.Node
import com.mx.rockstar.kratospoc.core.model.kratos.UserInterface

object MockUtil {

    /**
     * The function creates a mock user interface with a specified action, method, and nodes.
     */
    fun mockUserInterface() = UserInterface(
        action = "https://www.occdev.com.mx/kratos/self-service/login?flow=cdf6a844-f436-425f-bd17-89524b9848c3",
        method = "POST",
        nodes = nodes()
    )

    private fun nodes(): List<Node> {
        return listOf(
            Node(
                type = "input",
                group = "default",
                attributes = attributes(),
                meta = Meta(label = Label(id = 1070004, text = "ID", type = "info"))
            )
        )
    }

    private fun attributes(): Attributes {
        return Attributes(
            name = "identifier",
            type = "text",
            value = "",
            required = true,
            autocomplete = null,
            disabled = false,
            nodeType = "input"
        )
    }

}
