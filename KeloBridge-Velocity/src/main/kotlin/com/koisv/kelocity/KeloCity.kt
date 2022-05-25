package com.koisv.kelocity

import com.google.inject.Inject
import com.koisv.kelocity.command.DevSend
import com.koisv.kelocity.listeners.DevTermListener
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import org.slf4j.Logger

@Plugin(
    id = "kelocity", name = "Kelocity", version = "0.1-SNAPSHOT",
    description = "KeiKoi?", url = "https://www.koisv.com", authors = ["KeiKoi"]
) // resources 폴더에다 velocity-plugin.json 작성 잊지 말기
class KeloCity {
    companion object {
        lateinit var server: ProxyServer
            private set
        lateinit var logger: Logger
            private set
        lateinit var instance: KeloCity
            private set

        // 네임스페이스:채널명 | namespace:channel -> 통신 채널 설정
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

        // 테스트용 명령어
        server.commandManager.register(DevSend.register())

        // 메인 채널 등록
        server.channelRegistrar.register(dev_In)
        server.channelRegistrar.register(dev_Out)

        // 채널 리스너 등록
        server.eventManager.register(this, DevTermListener())
    }
}