package com.koisv.kelopaper

import com.koisv.kelopaper.listeners.ActTerminal
import com.koisv.kelopaper.listeners.Events
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class KeloPaper : JavaPlugin() {
    companion object {
        // 채널 형식 [네임스페이스:채널명] 필히 준수해야 함.
        const val act_OUT = "kelocity:act-man"
        const val act_IN = "kelocity:act-sub"

        lateinit var instance : KeloPaper
            private set
    }

    override fun onEnable() {
        saveDefaultConfig()
        instance = this
        channelRegister()
        server.pluginManager.registerEvents(Events(),this)
    }

    private fun channelRegister() {
        // 채널 송수신 등록. 이때 리스너는 PluginMessageListener 를 상속해야 함.
        Bukkit.getMessenger().registerIncomingPluginChannel(this, act_IN, ActTerminal())
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, act_OUT)
    }

    override fun onDisable() {
        saveConfig()
    }
}