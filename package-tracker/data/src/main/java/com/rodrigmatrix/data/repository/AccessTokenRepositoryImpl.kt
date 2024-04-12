package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.local.AccessTokenDataSource
import com.rodrigmatrix.data.model.TokenRequest
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.data.service.RequestTokenService
import com.rodrigmatrix.domain.repository.AccessTokenRepository

class AccessTokenRepositoryImpl(
    private val requestTokenService: RequestTokenService,
    private val accessTokenDataSource: AccessTokenDataSource,
    private val requestTokenUrl: String,
    private val validateTokenUrl: String,
) : AccessTokenRepository {

    override fun getAccessToken(): String {
        return accessTokenDataSource.getAccessToken().orEmpty()
    }

    override suspend fun updateAccessToken(): String {
        val requestToken = requestTokenService.getRequestToken(requestTokenUrl)

        val token = requestTokenService.validateToken(
            url = validateTokenUrl,
            tokenRequest = TokenRequest(requestToken.requestToken)
        ).token.orEmpty()

        accessTokenDataSource.setAccessToken(token)

        return token
    }

}