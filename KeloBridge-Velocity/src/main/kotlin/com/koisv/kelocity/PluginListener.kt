@file:Suppress("UnstableApiUsage")

package com.koisv.kelocity

import com.koisv.kelocity.KeloCity.Companion.kickedServers
import com.koisv.kelocity.managers.PlayerManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.KickedFromServerEvent
import com.velocitypowered.api.event.player.ServerPostConnectEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class PluginListener {
    private val prefix = Component.text("시스템 ").color(TextColor.color(70,255,70))
        .append(Component.text(">> ").color(TextColor.color(150,150,150)))
        .append(Component.text("").color(TextColor.color(255,255,255)))

    @Subscribe
    fun onDevReceive(e: PluginMessageEvent) {
        val arrayData = e.data
        val target = e.identifier.id
        val receiver = DataInputStream(ByteArrayInputStream(arrayData))
        try {
            val data = receiver.readUTF()
            KeloCity.logger.debug("$target : $data")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Subscribe
    fun onActReceive(e: PluginMessageEvent) {
        val arrayData = e.data
        val target = e.identifier.id
        val receiver = DataInputStream(ByteArrayInputStream(arrayData))
        if (target.startsWith("kelocity:act"))
            try {
                when (receiver.readUTF()) {
                    "PlayerSend" ->
                        PlayerManager.send(receiver.readUTF(), receiver.readUTF())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
    }

    @Subscribe
    fun kickMessage(e: KickedFromServerEvent) {
        try {
            if (e.kickedDuringServerConnect())
                e.player.sendMessage(prefix.append(Component.text(
                    "접속하려던 서버에 문제가 발생했습니다 : ${(e.serverKickReason.get() as TextComponent).content()}"
                )))
            else {
                e.player.sendMessage(prefix.append(Component.text(
                    "접속중인 서버에 문제가 발생했습니다 : ${(e.serverKickReason.get() as TextComponent).content()}"
                )))
                kickedServers[e.player] = e.server
            }
        } catch (_: Exception) { }
    }

    @Subscribe
    fun onServerChange(e: ServerPostConnectEvent) {
        val curServer = e.player.currentServer.get()
        val prvServer = e.previousServer
        if (prvServer != null) {
            KeloCity.server.allPlayers.forEach {
                it.sendMessage(Component.text(
                    "[${prvServer.serverInfo.name} -> ${curServer.serverInfo.name}] - ${e.player.username}"
                ))
            }
        } else {
            KeloCity.server.allPlayers.forEach {
                it.sendMessage(Component.text(
                    "[+] [${curServer.serverInfo.name}] - ${e.player.username}"
                ))
            }
        }
    }

    @Subscribe
    fun onDisconnect(e: DisconnectEvent) {
        if (e.loginStatus == DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) {
            val curServer = e.player.currentServer.get()
            KeloCity.server.allPlayers.forEach {
                it.sendMessage(Component.text(
                    "[-] [${curServer.serverInfo.name}] - ${e.player.username}"
                ))
            }
            kickedServers.remove(e.player)
        }
    }
}