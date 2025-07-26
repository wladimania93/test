package com.example.parchis

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameClient(
    private val scope: CoroutineScope
) {
    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    private var session: DefaultClientWebSocketSession? = null

    val incomingMessages = Channel<String>(Channel.UNLIMITED)

    fun connect(host: String, port: Int) {
        scope.launch {
            try {
                client.webSocket(method = HttpMethod.Get, host = host, port = port, path = "/ws") {
                    session = this
                    withContext(Dispatchers.IO) {
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                incomingMessages.send(frame.readText())
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle connection error
            }
        }
    }

    fun sendMessage(message: String) {
        scope.launch {
            session?.send(message)
        }
    }

    fun disconnect() {
        scope.launch {
            session?.close()
            client.close()
        }
    }
}
