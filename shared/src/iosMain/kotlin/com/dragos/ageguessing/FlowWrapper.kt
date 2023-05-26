package com.dragos.ageguessing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 * This class is used to create a flow, used in the iOS app
 */
class FlowWrapper<T: Any>(
    private val scope: CoroutineScope,
    private val flow: Flow<T>
) {

    /**
     * Method that creates a flow, used in the iOS app
     */
    fun subscribe(
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ) = flow
        .onEach { onEach(it) }
        .catch { onThrow(it) }
        .onCompletion { onComplete() }
        .launchIn(scope)
}