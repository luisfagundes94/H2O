package com.luisfagundes.h2o.core.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatchers: H2oDispatchers)

enum class H2oDispatchers {
    Default,
    IO,
}
