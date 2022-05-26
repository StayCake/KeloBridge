package com.koisv.kelocity.commands

import com.koisv.kelocity.KeloCity
import com.koisv.kelocity.KeloMessageTerminal
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException


object Server {
    // 브리가디어 명령어 [유사 Kommand 모장 버전]
    fun register() : BrigadierCommand {
        val helloNode = LiteralArgumentBuilder
            .literal<CommandSource>("s")
            .executes {
                val byteArray = ByteArrayOutputStream()
                val outputStream = DataOutputStream(byteArray)
                try {
                    outputStream.writeUTF("serverGuiOpen") // 일반 유니코드 문자열 전송, 종류에 따라 메소드 변경 가능.\
                    outputStream.writeUTF((it.source as Player).username)
                    outputStream.writeInt(KeloCity.server.allServers.size)
                    for (i in KeloCity.server.allServers) {
                        try {
                            outputStream.writeInt(i.ping().get().players.get().online)
                        } catch (e: Exception) {
                            outputStream.writeInt(-1)
                        }
                        outputStream.writeBoolean(
                            (it.source as Player).currentServer.get().server == i
                        )
                        outputStream.writeUTF(i.serverInfo.name)
                    }
                    // 송,수신 데이터 순서 맞추기 필수
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                KeloMessageTerminal(
                    KeloCity.act_OUT,
                    KeloCity.server.getServer(
                        (it.source as Player).currentServer.get().serverInfo.name
                    ).get(),
                    byteArray
                ).run()
                1
            }
            .build()
        return BrigadierCommand(helloNode)
    }
}