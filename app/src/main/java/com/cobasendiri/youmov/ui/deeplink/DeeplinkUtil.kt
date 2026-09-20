package com.cobasendiri.youmov.ui.deeplink

object DeeplinkUtil {
    const val DEEPLINK_BASE_URL = "https://youmov.com"

    fun generateDetailDeepLink(movieId: Int): String {
        return "$DEEPLINK_BASE_URL/detail/$movieId"
    }
}