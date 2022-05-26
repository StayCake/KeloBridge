package com.koisv.kelocity

import com.koisv.kelocity.managers.PlayerManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class PluginListener {
    @Subscribe
    fun onDevReceive(event: PluginMessageEvent) {
        val arrayData = event.data
        val target = event.identifier.id
        val receiver = DataInputStream(ByteArrayInputStream(arrayData))
        try {
            val data = receiver.readUTF()
            KeloCity.logger.debug("$target : $data")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Subscribe
    fun onActReceive(event: PluginMessageEvent) {
        val arrayData = event.data
        val target = event.identifier.id
        val receiver = DataInputStream(ByteArrayInputStream(arrayData))
        try {
            when (receiver.readUTF()) {
                "PlayerSend" ->
                    if (target.startsWith("kelocity:act"))
                        PlayerManager.send(receiver.readUTF(),receiver.readUTF())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}