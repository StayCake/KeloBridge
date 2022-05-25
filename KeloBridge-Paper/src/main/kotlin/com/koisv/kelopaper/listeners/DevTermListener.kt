package com.koisv.kelopaper.listeners

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class DevTermListener : PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        val receiver = DataInputStream(ByteArrayInputStream(message)) // 스트림에서 바로 읽어오기
        try {
            val data = receiver.readUTF() // 순차 수신 [송신 갯수와 수신 갯수가 같아야 함]
            println("[ $channel ] ${player.name} : $data") // 테스트용 출력
            // 정상 출력 예 : [ kelocity:dev-sub ] Komeiji__Koishi : DATA
        } catch ( e: IOException ) {
            e.printStackTrace()
        }
    }

}