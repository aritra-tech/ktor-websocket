package com.aritra.plugins

import com.aritra.data.model.ChatSession
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val name = call.parameters["name"] ?: "Guest"
            call.sessions.set(ChatSession(name, generateNonce()))
        }
    }
}
