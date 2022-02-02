package com.rodrigmatrix.domain.repository

interface NotificationsRepository {

    fun sendPackageUpdateNotification(title: String, description: String)

    fun sendLinkNotification(title: String, description: String, link: String)
}