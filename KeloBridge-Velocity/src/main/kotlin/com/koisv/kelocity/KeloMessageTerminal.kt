package com.koisv.kelocity

import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import java.io.ByteArrayOutputStream


// 채널 송신 클래스
class KeloMessageTerminal(
    channel: ChannelIdentifier,
    target: RegisteredServer,
    data: ByteArrayOutputStream
) : Runnable {

    private val channel: ChannelIdentifier // 타깃 채널 [KeloCity.kt 참고]
    private val target: RegisteredServer // 타깃 서버
    private val data: ByteArrayOutputStream // 데이터

    init {
        this.channel = channel
        this.target = target
        this.data = data
    }

    override fun run() {
        // 타깃 서버에 플러그인 메시지 전송
        target.sendPluginMessage(channel, data.toByteArray())
    }
}