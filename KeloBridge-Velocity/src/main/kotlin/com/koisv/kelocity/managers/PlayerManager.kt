package com.koisv.kelocity.managers

import com.koisv.kelocity.KeloCity

object PlayerManager {
    fun send(victim: String, target: String) {
        val user = KeloCity.server.getPlayer(victim).get()
        user.createConnectionRequest(
            KeloCity.server.getServer(target).get()
        ).connect()
    }
}