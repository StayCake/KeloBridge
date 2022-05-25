package com.koisv.kelopaper.commands

import io.github.monun.kommand.node.LiteralNode

object Test {
    fun register(node: LiteralNode) {
        node.requires { hasPermission(4) }
        node.executes {  }
    }
}