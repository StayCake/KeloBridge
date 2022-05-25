package com.koisv.kelocity.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class DevTermListener {
    @Subscribe
    fun onReceive(event: PluginMessageEvent) {
        val arrayData = event.data
        val target = event.identifier.id
        val receiver = DataInputStream(ByteArrayInputStream(arrayData))
        try {
            val data = receiver.readUTF()
            println("$target : $data")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}