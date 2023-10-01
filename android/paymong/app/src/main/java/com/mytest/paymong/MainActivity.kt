package com.mytest.paymong

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mytest.paymong.socket.WebSocketClient
import com.mytest.paymong.socket.MyWebSocketListener
import com.mytest.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener


class MainActivity : ComponentActivity() {

    private lateinit var webSocketClient : WebSocketClient
    private val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webSocketClient = WebSocketClient()

        setContent {
            PaymongTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("TEXT")
                        Button(onClick = {
                            connect()
                        }) {
                            Text("연결")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text("전송")
                        }
                        Button(onClick = {
                            val intent:Intent = Intent(context, UITestActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text("내비 테스트 이동")
                        }
                        Button(onClick = {
                            val intent:Intent = Intent(context, RemoteTest::class.java)
                            startActivity(intent)
                        }) {
                            Text("워치 연동 테스트 이동")
                        }
                    }
                }
            }
        }


    }

    private fun connect(){
        lifecycleScope.launch {
            webSocketClient.connectWebSocket()
        }
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaymongTheme {
        Greeting("Android")
    }
}*/