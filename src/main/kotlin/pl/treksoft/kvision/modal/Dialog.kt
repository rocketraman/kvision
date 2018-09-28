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
package pl.treksoft.kvision.modal

import kotlinx.coroutines.suspendCancellableCoroutine
import pl.treksoft.kvision.core.Widget
import kotlin.coroutines.resume

/**
 * Modal window with a result.
 *
 * @constructor
 * @param caption window title
 * @param closeButton determines if Close button is visible
 * @param size modal window size
 * @param animation determines if animations are used
 * @param escape determines if dialog can be closed with Esc key
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
open class Dialog<R>(
    caption: String? = null, closeButton: Boolean = true,
    size: ModalSize? = null, animation: Boolean = true, escape: Boolean = true,
    classes: Set<String> = setOf(), init: (Dialog<R>.() -> Unit)? = null
) : Modal(caption, closeButton, size, animation, escape, classes) {

    internal var resultCallback: ((R?) -> Unit)? = null

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    /**
     * A suspending function returning result value.
     */
    suspend fun getResult(): R? = suspendCancellableCoroutine { cont ->
        resultCallback = { result ->
            resultCallback = null
            hide()
            cont.resume(result)
        }
        show()
    }

    /**
     * A function to be called with a result value.
     */
    open fun setResult(result: R?) {
        resultCallback?.invoke(result)
    }

    override fun hide(): Widget {
        resultCallback?.invoke(null)
        return super.hide()
    }
}
