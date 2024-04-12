package com.rodrigmatrix.data.interceptors

import com.rodrigmatrix.domain.repository.AccessTokenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

private const val TOKEN_HEADER = "app-check-token"

class AccessTokenInterceptor(
    private val accessTokenRepository: AccessTokenRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = accessTokenRepository.getAccessToken()

        val response = chain.proceed(newRequestWithAccessToken(accessToken, request))

        return if (response.code == HttpURLConnection.HTTP_FORBIDDEN) {
            val newAccessToken = runBlocking(coroutineDispatcher) {
                accessTokenRepository.updateAccessToken()
            }
            chain.proceed(newRequestWithAccessToken(newAccessToken, request))
        } else {
            response
        }
    }

    private fun newRequestWithAccessToken(accessToken: String, request: Request): Request =
        request.newBuilder()
            .header(TOKEN_HEADER, accessToken)
            .build()
}

