/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.mytest.paymong

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mytest.paymong.presentation.theme.PaymongTheme
import com.mytest.paymong.socket.WebSocketClient
import kotlinx.coroutines.launch

class WatchMainActivity : ComponentActivity() {
    private lateinit var webSocketClient : WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webSocketClient = WebSocketClient()
        setContent {
            WearApp(this)

        }
    }
    fun connect(){
        lifecycleScope.launch {
            webSocketClient.connectWebSocket()
        }
    }
}



@Composable
fun WearApp(activity: WatchMainActivity) {
    PaymongTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("TEXT")
            Button(onClick = {
                activity.connect()//?
            }) {
                Text("연결")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("전송")
            }
            Button(onClick = {
                val intent: Intent = Intent(activity, RemoteTest::class.java)
                activity.startActivity(intent)
            }) {
                Text("remote 이동")
            }
        }
    }

}
fun connect(){

}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    //WearApp("Preview Android")
}