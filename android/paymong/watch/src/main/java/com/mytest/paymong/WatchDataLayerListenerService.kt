package com.mytest.paymong

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class WatchDataLayerListenerService : WearableListenerService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        when (messageEvent.path) {
            START_WEAR_ACTIVITY_PATH -> {
                // 모바일 기기와 연동시 playId 저장 후 앱 실행
                val playerId = messageEvent.data.decodeToString()
                Log.d("페이몽","워치 WatchDataLayerListenerService 수신 - "+playerId)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val START_WEAR_ACTIVITY_PATH = "/start-activity"
    }
}