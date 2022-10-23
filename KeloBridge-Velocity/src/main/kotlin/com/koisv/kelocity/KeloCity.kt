package com.koisv.kelocity

import com.google.inject.Inject
import com.koisv.kelocity.commands.Server
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import org.slf4j.Logger

@Plugin(
    id = "kelocity", name = "Kelocity", version = "0.41-SNAPSHOT",
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

        val kickedServers = mutableMapOf<Player, RegisteredServer>()

        // 네임스페이스:채널명 | namespace:channel -> 통신 채널 설정
        val act_IN: MinecraftChannelIdentifier = MinecraftChannelIdentifier.create("kelocity","act-man")
        val act_OUT: MinecraftChannelIdentifier = MinecraftChannelIdentifier.create("kelocity","act-sub")
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
        server.commandManager.register(Server.register())

        // 메인 채널 등록
        server.channelRegistrar.register(act_IN)
        server.channelRegistrar.register(act_OUT)

        // 채널 리스너 등록
        server.eventManager.register(this, PluginListener())

        /*server.scheduler.buildTask(this, Runnable {
            if (kickedServers.isNotEmpty()) {
                kickedServers.values.forEach {
                    try {
                        kickedServers.filterValues { s -> s == it }
                            .forEach { (p, s) ->
                                it.ping().whenComplete { _, _ ->
                                    p.createConnectionRequest(s).connect().whenComplete { a, _ ->
                                        if (a.status == Status.SUCCESS) {
                                            kickedServers.remove(p)
                                            p.sendMessage(
                                                Component.text(">> 마지막으로 접속중이던 서버로 자동 복귀되었습니다.")
                                            )
                                        }
                                    }.get(500L, TimeUnit.MILLISECONDS)
                                }.get(500L, TimeUnit.MILLISECONDS)
                            }
                    } catch (_: Exception) { }
                }
            }
        }).repeat(1, TimeUnit.SECONDS).schedule()*/
    }

    /*@Subscribe
    fun shutdown(event: ProxyShutdownEvent) {
        server.scheduler.tasksByPlugin(this).forEach { it.cancel() }
    }*/
}