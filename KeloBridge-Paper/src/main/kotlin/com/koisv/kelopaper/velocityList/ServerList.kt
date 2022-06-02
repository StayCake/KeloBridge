package com.koisv.kelopaper.velocityList

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui
import com.github.stefvanschie.inventoryframework.pane.StaticPane
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil

object ServerList {
    data class ServerInfo(
        val playerConnected: Boolean,
        val name: String,
        val version: String?,
        val online: Int?,
        val mods: Int?,
    )

    fun serverMenu(data: List<ServerInfo>, req: Player) : ChestGui {
        val lineSize = ceil((data.size.toDouble()) / 9).toInt()
        val menu = ChestGui(lineSize, "서버 선택")
        val menuPane = StaticPane(9, lineSize)
        data.forEach {
            val name = it.name
            val current = it.playerConnected
            val online = if (it.online != null) "${it.online}명 접속중" else "서버 오프라인"
            val serverIcon = GuiItem(
                ItemStack(
                    when {
                        (it.online != null && it.mods == null && !current) -> Material.GREEN_CONCRETE
                        (it.online != null && it.mods == null) -> Material.GRASS_BLOCK
                        (it.online != null && !current) -> Material.ANVIL
                        (it.online != null) -> Material.CRAFTING_TABLE
                        else -> Material.RED_CONCRETE
                    },
                    if ((it.online ?: 1) > 64 || it.online == 0) 1 else it.online ?: 1
                ).apply {
                    itemMeta = itemMeta.apply {
                        displayName(
                            if (req.protocolVersion < 393)
                                Component.text("§f$name")
                            else Component.text(name).decoration(TextDecoration.ITALIC,false)
                        )
                        lore(listOf(
                            Component.text(online).color(
                                if (it.online != null) TextColor.color(255,255,255)
                                else TextColor.color(255,0,0)
                            ).decoration(TextDecoration.ITALIC,false),
                        ))
                        lore(lore()?.apply {
                            if (it.playerConnected) {
                                add(
                                    Component.text("현재 접속 중")
                                        .color(TextColor.color(0, 200, 200))
                                        .decoration(TextDecoration.ITALIC, false)
                                )
                            }
                            if (it.version != null) {
                                add(
                                    Component.text(it.version)
                                        .color(TextColor.color(255,125,255))
                                        .decoration(TextDecoration.ITALIC, false)
                                )
                            }
                            if (it.mods != null) {
                                add(
                                    Component.text("모드 ")
                                        .color(TextColor.color(255,180,0))
                                        .decoration(TextDecoration.ITALIC, false)
                                        .append(Component.text(it.mods)
                                            .color(TextColor.color(105,180,90)))
                                        .append(Component.text("개"))
                                )
                            }
                        })
                    }
                }
            ) { e ->
                e.isCancelled = true
                when {
                    it.online == null -> {
                        e.clickedInventory?.close()
                        e.whoClicked.playSound(Sound.sound(Key.key("block.note_block.bell"),Sound.Source.PLAYER,1F,2F))
                        e.whoClicked.sendMessage("§a시스템 §7>> §f해당 서버는 현재 §c오프라인 §f입니다!")
                    }
                    it.playerConnected -> {
                        e.clickedInventory?.close()
                        e.whoClicked.playSound(Sound.sound(Key.key("block.note_block.bell"),Sound.Source.PLAYER,1F,2F))
                        e.whoClicked.sendMessage("§a시스템 §7>> §f이미 §c접속 중인 서버 §f입니다!")
                    }
                    else -> {
                        PlayerSend.send(it,e.whoClicked as Player)
                    }
                }
            }
            menuPane.addItem(serverIcon,
                data.indexOf(it).rem(9),
                data.indexOf(it).div(9)
            )
        }
        menu.addPane(menuPane)
        menu.update()
        return menu
    }
}