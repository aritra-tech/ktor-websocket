package com.aritra.routes

import com.aritra.data.model.ChatSession
import com.aritra.room.RoomController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSession(roomController: RoomController) {
    webSocket("/chat-socket") {
        val sessions = call.sessions.get<ChatSession>()
        if (sessions == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY,"No sessions"))
            return@webSocket
        }

        roomController.join(
            name = sessions.name,
            sessionId = sessions.sessionId,
            socket = this
        )

        incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                roomController.sendMessage(
                    senderName = sessions.name,
                    message = frame.readText()
                )
            }
        }
    }
}

fun Route.getAllMessages(roomController: RoomController) {
    get("/message") {
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllMessage()
        )
    }
}