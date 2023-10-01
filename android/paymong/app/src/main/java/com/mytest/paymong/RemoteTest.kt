package com.mytest.paymong

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.mytest.paymong.ui.theme.PaymongTheme
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteTest : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {



    // 모바일-워치 간 연결
    private lateinit var capabilityClient: CapabilityClient
    private lateinit var nodeClient: NodeClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper
    private lateinit var messageClient: MessageClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        capabilityClient = Wearable.getCapabilityClient(this)
        nodeClient = Wearable.getNodeClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)
        setContent {

            Main(capabilityClient, nodeClient, remoteActivityHelper, messageClient)


        }
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
        Log.d("페이몽","연결 변화생김")
    }

}

@Composable
fun Main(capabilityClient: CapabilityClient,
         nodeClient: NodeClient,
         remoteActivityHelper: RemoteActivityHelper,
         messageClient: MessageClient,
         ){
    val START_WEAR_ACTIVITY_PATH = "/start-activity"
    val CAPABILITY_WEAR_APP = "watch_paymong"
    var installedWearNodes: Set<Node>? = null //앱설치된 노드들
    var connectedWearNodes: List<Node>? = null //연결된 노드들
    var wearNodeWithApp : Node?=null //

    var watchId :String?= null

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            //연결된(블루투스) 워치 정보 얻기
            CoroutineScope(Dispatchers.IO).launch {
                connectedWearNodes = Tasks.await(nodeClient.connectedNodes)
                Log.d("페이몽", connectedWearNodes.toString())
            }
        }) {
            Text(text = "연결된 웨어노드 정보")
        }

        Button(onClick = {
            //앱 설치한 워치 노드 정보 얻기
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val capabilityInfo: CapabilityInfo = Tasks.await(
                        capabilityClient
                            .getCapability(
                                CAPABILITY_WEAR_APP,
                                CapabilityClient.FILTER_REACHABLE
                            )
                    )

                    installedWearNodes = capabilityInfo.nodes
                    if (installedWearNodes != null) {
                        for (node in installedWearNodes!!) {
                            watchId = node.id
                            break;
                        }
                    }
                    Log.d("페이몽","앱설치한 웨어노드"+installedWearNodes + " "+watchId)
                }
            } catch (e: Exception) {
                Log.d("페이몽",e.toString())
            }

        }) {
            Text(text = "앱설치한 웨어노드 정보")
        }

        Button(onClick = {
            //연결된 노드중 앱이 설치 안된 경우
            if(connectedWearNodes==null || installedWearNodes==null){
                Log.d("페이몽", "노드정보 없음")
                return@Button
            }

            val PLAY_STORE_APP_URI = "market://details?id=com.paymong" //"https://naver.com"
            for(node in connectedWearNodes!!){
                if(installedWearNodes?.contains(node)==false){ //연결된 노드중 앱설치 안된 노드면
                    try{
                        val intent = Intent(Intent.ACTION_VIEW)
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                            .setData(Uri.parse(PLAY_STORE_APP_URI))

                        CoroutineScope(Dispatchers.IO).launch {
                            remoteActivityHelper
                                .startRemoteActivity(
                                    targetIntent = intent,
                                    targetNodeId = node.id
                                )
                        }
                    }catch (e:Exception){
                        Log.d("페이몽",e.toString())
                    }
                }
            }
        }) {
            Text(text = "플레이스토어 실행")
        }

        Button(onClick = {
            if(watchId==null){
                Log.d("페이몽", "노드정보 없음")
                return@Button
            }
            val playerId = "구글플레이id"
            CoroutineScope(Dispatchers.IO).launch {
                messageClient.sendMessage(
                    watchId!!,
                    START_WEAR_ACTIVITY_PATH,
                    playerId.toByteArray()
                )
            }
        }) {
            Text(text = "앱 메시지 보내기")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    PaymongTheme {
        //Main()
    }
}