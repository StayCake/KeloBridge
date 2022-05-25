package com.koisv.kelopaper.listeners

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class DevTermListener : PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        val receiver = DataInputStream(ByteArrayInputStream(message))
        try {
            val data = receiver.readUTF()
            println("[ $channel ] ${player.name} : $data")
        } catch ( e: IOException ) {
            e.printStackTrace()
        }
    }

}