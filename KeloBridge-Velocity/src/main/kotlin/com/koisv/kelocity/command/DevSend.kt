package com.koisv.kelocity.command

import com.koisv.kelocity.KeloCity
import com.koisv.kelocity.KeloMessageTerminal
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException


object DevSend {
    // 브리가디어 명령어 [유사 Kommand 모장 버전]
    fun register() : BrigadierCommand {
        val helloNode = LiteralArgumentBuilder
            .literal<CommandSource>("dev")
            .then(
                argument<CommandSource?, String?>("data",string())
                    .executes { c ->
                        val data = getString(c, "data")
                        val byteArray = ByteArrayOutputStream()
                        val out = DataOutputStream(byteArray)
                        try {
                            out.writeUTF(data) // 일반 유니코드 문자열 전송, 종류에 따라 메소드 변경 가능.
                            // 송,수신 데이터 순서 맞추기 필수
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        KeloMessageTerminal(
                            KeloCity.dev_Out,
                            KeloCity.server.getServer(
                                (c.source as Player).currentServer.get().serverInfo.name
                            ).get(),
                            byteArray
                        ).run()
                        1
                    }
            )
            .build()
        return BrigadierCommand(helloNode)
    }
}