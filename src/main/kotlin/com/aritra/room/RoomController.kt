package com.aritra.room

import com.aritra.data.model.Message
import com.aritra.data.repository.MessageDataSource
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {

    private val members = ConcurrentHashMap<String, Member>()

    fun join(
        name: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        members[name] = Member(
            name = name,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(senderName: String, message: String) {
        members.values.forEach { members ->
            val messageEntity = Message(
                text = message,
                name = senderName,
                time = System.currentTimeMillis()
            )

            messageDataSource.addMessage(messageEntity)

            val parsedMessage = Json.encodeToString(messageEntity)
            members.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessage(): List<Message> {
        return messageDataSource.getAllMessages()
    }
}