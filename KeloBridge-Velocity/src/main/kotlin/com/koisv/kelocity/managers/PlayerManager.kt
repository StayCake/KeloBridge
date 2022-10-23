package com.koisv.kelocity.managers

import com.koisv.kelocity.KeloCity
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component

object PlayerManager {
    fun send(victim: String, target: String) {
        val user = KeloCity.server.getPlayer(victim).get()
        user.createConnectionRequest(
            KeloCity.server.getServer(target).get()
        ).connect().thenAccept { result ->
            if (!result.isSuccessful) {
                if (result.reasonComponent.isPresent) {
                    user.playSound(
                        Sound.sound(Key.key("block.note_block.bell"),Sound.Source.PLAYER,1F,2F)
                    )
                    user.sendMessage(
                        Component.text(">> 접속에 실패했습니다 : ").append(
                            result.reasonComponent.get()
                        )
                    )
                }
            }
        }.join()
    }
}