package com.koisv.kelopaper

import com.koisv.kelopaper.listeners.DevTermListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class KeloPaper : JavaPlugin() {
    companion object {
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
        Bukkit.getMessenger().registerIncomingPluginChannel(this, CHANNEL_IN, DevTermListener())
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, CHANNEL_OUT)
    }
}