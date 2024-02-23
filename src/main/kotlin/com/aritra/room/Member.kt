package com.aritra.room

import io.ktor.websocket.*

data class Member(
    val name: String,
    val sessionId: String,
    val socket: WebSocketSession
)
