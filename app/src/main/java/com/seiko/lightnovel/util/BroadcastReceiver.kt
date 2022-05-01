package com.seiko.lightnovel.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

fun intentFilterOf(vararg actions: String) = IntentFilter().apply {
    for (action in actions) {
        addAction(action)
    }
}

fun IntentFilter.asFlow(context: Context) = callbackFlow {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            trySend(intent)
        }
    }
    context.registerReceiver(receiver, this@asFlow)
    awaitClose { context.unregisterReceiver(receiver) }
}

/**
 * 当前电量百分比
 */
fun batteryPercentFlow(context: Context): Flow<Float> =
    intentFilterOf(Intent.ACTION_BATTERY_CHANGED).asFlow(context)
        .map { intent ->
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level / scale.toFloat()
        }
