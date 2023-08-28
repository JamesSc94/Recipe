package com.jamessc94.recipe.network

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        return chain.proceed(request)
    }

}