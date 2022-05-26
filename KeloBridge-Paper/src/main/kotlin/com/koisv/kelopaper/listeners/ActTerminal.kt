package com.koisv.kelopaper.listeners

import com.koisv.kelopaper.KeloPaper
import com.koisv.kelopaper.velocityList.ServerList
import com.koisv.kelopaper.velocityList.ServerList.ServerInfo
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.IOException

class ActTerminal : PluginMessageListener {
    companion object {
        fun send(data: ByteArrayOutputStream) {
            KeloPaper.instance.server.sendPluginMessage(
                KeloPaper.instance, KeloPaper.act_OUT, data.toByteArray()
            )
        }
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        val receiver = DataInputStream(ByteArrayInputStream(message)) // 스트림에서 바로 읽어오기
        try {
            when (receiver.readUTF()) { // 순차 수신 [송신 갯수와 수신 갯수가 같아야 함]
                "serverGuiOpen" -> {
                    val victim = KeloPaper.instance.server.getPlayer(receiver.readUTF())
                    val amount = receiver.readInt()
                    val finalList = mutableListOf<ServerInfo>()
                    if (amount == 0) return
                    for (t in 1..amount) {
                        val online = receiver.readInt()
                        finalList.add(
                            ServerInfo(
                                receiver.readBoolean(),
                                receiver.readUTF(),
                                if (online == -1) null else online
                            )
                        )
                    }
                    victim?.let { ServerList.serverMenu(finalList).show(it) }
                }
            }
        } catch ( e: IOException ) {
            e.printStackTrace()
        }
    }
}