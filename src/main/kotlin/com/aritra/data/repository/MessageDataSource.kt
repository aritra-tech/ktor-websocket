package com.aritra.data.repository

import com.aritra.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun addMessage(message: Message)
}