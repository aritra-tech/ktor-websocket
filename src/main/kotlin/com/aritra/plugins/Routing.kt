package com.aritra.plugins

import com.aritra.room.RoomController
import com.aritra.routes.chatSession
import com.aritra.routes.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val roomController by inject<RoomController>()

    routing {
        chatSession(roomController)
        getAllMessages(roomController)
    }
}
