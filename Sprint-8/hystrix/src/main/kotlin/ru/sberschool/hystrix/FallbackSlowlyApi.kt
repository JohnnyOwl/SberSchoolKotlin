package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getBerry() = Berry("fallback")
}

