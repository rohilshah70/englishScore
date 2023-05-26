package com.dragos.ageguessing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 *  Swift interoperability helper
 */
object SwiftHelper {

    /**
     * Creates a coroutine scope
     */
    fun createScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    /**
     * Clears a scope
     */
    fun clearScope(scope: CoroutineScope) {
        scope.coroutineContext.cancel()
    }

    /**
     *  An method which creates a flow, as flows are not supported in Swift
     */
    fun <T: Any>createFlow(data: T?) = flowOf(data)

    /**
     * An method which creates a flow wrapper
     */
    fun <T: Any>getFlowWrapper(scope: CoroutineScope, flow: Flow<T>) : FlowWrapper<T> {
        return FlowWrapper(scope, flow)
    }

    /**
     *  An method which creates a result wrapper, as Kotlin Result is not supported in Swift
     */
    fun <T: Any>sendResult(value: T, success: Boolean = true) : Result<Result<T>> {
        return if (success) {
            Result.success(Result.success(value))
        } else {
            val error =  if (value is String) value else "Failed to send result"
            Result.failure(Throwable(error))
        }
    }

    /**
     *  An method which creates a custom result from a kotlin result, as Kotlin Result is not supported in Swift
     */
    fun <T: Any>getResult(result: Result<T>?) : CustomResultSwift<T> {
        return  if (result!!.isSuccess) {
            CustomResultSwift.success(result.getOrThrow())
        }
        else {
            CustomResultSwift(null, result.exceptionOrNull())
        }
    }
}

/**
 *  A custom result wrapper, as Kotlin Result is not supported in Swift
 */
class CustomResultSwift<T: Any>(private val _value: T?, private val _error: Throwable?) {
    companion object {
        /**
         *  An method which returns a successful custom result with the given value.
         */
        fun <T: Any>success(value: T): CustomResultSwift<T> =
            CustomResultSwift(value, null)
        /**
         *  An method which returns a failed custom result with a null value.
         */
        fun <T: Any> failure(error: Throwable): CustomResultSwift<T> =
            CustomResultSwift(null, error)
    }

    val isSuccess: Boolean get() = _error == null
    val isFailure: Boolean get() = _error != null
    val value: T? get() = _value
    val error: Throwable? get() = _error
}