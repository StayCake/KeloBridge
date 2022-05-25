package com.koisv.kelopaper

import com.koisv.kelopaper.listeners.DevTermListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class KeloPaper : JavaPlugin() {
    companion object {
        // 채널 형식 [네임스페이스:채널명] 필히 준수해야 함.
        const val CHANNEL_OUT = "kelocity:dev-man"
        const val CHANNEL_IN = "kelocity:dev-sub"

        lateinit var instance : KeloPaper
            private set
    }

    override fun onEnable() {
        instance = this
        channelRegister()
    }

    private fun channelRegister() {
        // 채널 송수신 등록. 이때 리스너는 PluginMessageListener 를 상속해야 함.
        Bukkit.getMessenger().registerIncomingPluginChannel(this, CHANNEL_IN, DevTermListener())
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, CHANNEL_OUT)
    }
}