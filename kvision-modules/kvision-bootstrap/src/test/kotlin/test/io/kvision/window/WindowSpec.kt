/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.io.kvision.window

import kotlinx.browser.document
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.window.Window
import kotlin.test.Test

class WindowSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val window = Window("Window title", isResizable = false)
            root.add(window)
            window.show()
            val id = window.id
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"modal-content kv-window\" id=\"$id\" style=\"position: absolute; overflow: hidden; width: auto; z-index: 901;\"><div class=\"modal-header\"><h5 class=\"modal-title\">Window title</h5><div class=\"kv-window-icons-container\"></div></div><div style=\"height: auto; overflow: auto;\"></div></div>",
                element?.innerHTML,
                "Should render floating window"
            )
        }
    }

}