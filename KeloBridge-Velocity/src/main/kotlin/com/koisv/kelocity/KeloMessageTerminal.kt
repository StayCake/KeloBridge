package com.koisv.kelocity

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import java.io.ByteArrayOutputStream

class KeloMessageTerminal(
    channel: ChannelIdentifier,
    target: RegisteredServer,
    data: ByteArrayOutputStream
) : Runnable {

    private val channel: ChannelIdentifier
    private val target: RegisteredServer
    private val data: ByteArrayOutputStream

    init {
        this.channel = channel
        this.target = target
        this.data = data
    }

    override fun run() {
        target.sendPluginMessage(channel, data.toByteArray())
    }
}