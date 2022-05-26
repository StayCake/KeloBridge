package com.koisv.kelopaper.velocityList

import com.koisv.kelopaper.listeners.ActTerminal
import org.bukkit.entity.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

object PlayerSend {
    fun send(target: ServerList.ServerInfo, victim: Player) {
        val arrayOutput = ByteArrayOutputStream()
        val outputStream = DataOutputStream(arrayOutput)
        try {
            outputStream.writeUTF("PlayerSend")
            outputStream.writeUTF(victim.name)
            outputStream.writeUTF(target.name)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        ActTerminal.send(arrayOutput)
    }
}