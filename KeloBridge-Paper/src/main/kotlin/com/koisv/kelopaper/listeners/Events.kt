package com.koisv.kelopaper.listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Events : Listener {
    private val dateFormat = DateTimeFormatter
        .ofPattern("yy/MM/dd | h:mm:ss a")

    @EventHandler
    private fun onJoinSetup(e: PlayerJoinEvent) {
        val current = LocalDateTime.now()
        val joinTime = current.format(dateFormat)
        e.joinMessage(
            Component.text("[").color(TextColor.color(255,150,0))
                .append(Component.text("+")).color(TextColor.color(30,235,30)).hoverEvent(
                    HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(joinTime))
                )
                .append(Component.text("] ")).color(TextColor.color(255,150,0))
                .append(Component.text((e.player.displayName() as TextComponent).content()))
        )
    }
}