package app.yokaicore.util

import rx.Observable
import yokaicore.core.util.lang.awaitSingle

actual suspend fun <T> Observable<T>.awaitSingle(): T = awaitSingle()
