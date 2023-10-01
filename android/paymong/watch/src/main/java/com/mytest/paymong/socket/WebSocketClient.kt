package com.mytest.paymong.socket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit


class WebSocketClient {
    private val CLOSED = "closed"
    private val CONNECTED = "connected"
    private var state = CLOSED
    private val url = "ws://k8e103.p.ssafy.io:8877/ws"

    private val client = OkHttpClient.Builder()
        .pingInterval(1, TimeUnit.SECONDS)
        .build()
    private lateinit var webSocket: WebSocket
    private val request = Request.Builder()
        .url(url)
        .addHeader("id","1")
        .addHeader("type","1")
        .build()


    fun connectWebSocket() {
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                state = CONNECTED
                Log.d("페이몽","onOpen")
                // 웹소켓 연결이 열렸을 때 실행되는 코드

            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("페이몽","onMessage")
                // 웹소켓으로부터 메시지를 수신했을 때 실행되는 코드
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                state = CLOSED
                Log.d("페이몽","onClosed")
                // 웹소켓 연결이 닫혔을 때 실행되는 코드
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("페이몽","onFailure" + response!!.message())
                // 웹소켓 연결에 실패했을 때 실행되는 코드
            }
        })

    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    fun disconnectWebSocket() {
        webSocket.close(1000, null)
        state = CLOSED
    }
}