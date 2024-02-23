package com.aritra.data.repository

import com.aritra.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImpl(
    db: CoroutineDatabase
): MessageDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find().descendingSort(Message::time).toList()
    }

    override suspend fun addMessage(message: Message) {
        messages.insertOne(message)
    }
}