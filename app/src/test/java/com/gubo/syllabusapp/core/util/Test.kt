package com.gubo.syllabusapp.core.util

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<T>.testItems(
    block: suspend ReceiveTurbine<T>.() -> Unit
) = test {
    block()
    cancelAndIgnoreRemainingEvents()
}