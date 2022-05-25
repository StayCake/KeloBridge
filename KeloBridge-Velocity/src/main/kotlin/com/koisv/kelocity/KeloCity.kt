package com.koisv.kelocity

import com.google.inject.Inject
import com.koisv.kelocity.command.DevSend
import com.koisv.kelocity.listeners.DevTermListener
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import net.kyori.adventure.text.Component
import org.slf4j.Logger

@Plugin(
    id = "kelocity", name = "Kelocity", version = "0.1-SNAPSHOT",
    description = "KeiKoi?", url = "https://www.koisv.com", authors = ["KeiKoi"]
)
class KeloCity {
    companion object {
        lateinit var server: ProxyServer
            private set
        lateinit var logger: Logger
            private set
        lateinit var instance: KeloCity
            private set

        val dev_In: MinecraftChannelIdentifier = MinecraftChannelIdentifier.create("kelocity","dev-man")
        val dev_Out: MinecraftChannelIdentifier = MinecraftChannelIdentifier.create("kelocity","dev-sub")
    }

    @Inject
    fun mainInit(Proxy: ProxyServer, Logger: Logger) {
        instance = this
        server = Proxy
        logger = Logger
        Logger.info("Main Initialize Complete.")
    }

    @Subscribe
    fun onProxyInit(event: ProxyInitializeEvent) {
        logger.info("Starting Up...")

        server.commandManager.register(DevSend.register())

        server.channelRegistrar.register(dev_In)
        server.channelRegistrar.register(dev_Out)

        server.eventManager.register(this, DevTermListener())
    }
}