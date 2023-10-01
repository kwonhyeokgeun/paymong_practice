package com.mytest.paymong.socket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response?) {
        webSocket.send("")
        Log.d("페이몽","onOpen")
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
        Log.d("Socket", "Receiving bytes : $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("페이몽","onClosing")
        webSocket.close(100, null)
        webSocket.cancel()
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        Log.d("Socket","Error : " + t.message)
    }

}