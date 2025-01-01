package com.fsd

import co.aikar.commands.PaperCommandManager
import com.fsd.commands.AcidRainCommand
import com.fsd.commands.LightningStormCommand
import com.fsd.commands.RevengeCommand
import com.fsd.config.ConfigManager
import com.fsd.listeners.PeacefulRevengeListener
import org.bukkit.plugin.java.JavaPlugin

class FsGreatCatastrophe : JavaPlugin() {

    private var isRevengeEnabled: Boolean = false

    override fun onEnable() {
        showStartupMessage()

        ConfigManager.initialize(this)

        val manager = PaperCommandManager(this)
        manager.registerCommand(AcidRainCommand(this))
        manager.registerCommand(RevengeCommand(this))
        manager.registerCommand(LightningStormCommand(this))

        server.pluginManager.registerEvents(PeacefulRevengeListener(this), this)
    }

    fun isRevengeEnabled(): Boolean {
        return isRevengeEnabled
    }

    fun setRevengeEnabled(value: Boolean) {
        isRevengeEnabled = value
    }

    override fun onDisable() {
        logger.info("§cGreatCatastrophe plugin disabled!")
    }

    private fun showStartupMessage() {
        val reset = "\u001B[0m"
        val orange1 = "\u001B[38;5;202m"
        val orange2 = "\u001B[38;5;208m"
        val orange3 = "\u001B[38;5;214m"
        val orange4 = "\u001B[38;5;220m"

        logger.info("")
        logger.info("                ${orange1}███████╗███████╗██████╗ ${reset}")
        logger.info("                ${orange2}██╔════╝██╔════╝██╔══██╗${reset}")
        logger.info("                ${orange3}█████╗  ███████╗██║  ██║${reset}")
        logger.info("                ${orange4}██╔══╝  ╚════██║██║  ██║${reset}")
        logger.info("                ${orange4}██║     ███████║██████╔╝${reset}")
        logger.info("                ${orange4}╚═╝     ╚══════╝╚═════╝ ${reset}")
        logger.info("${orange1}            Разработано Felleus Development${reset}")
        logger.info("${orange1}                https://felleusdev.com/${reset}")
        logger.info("")
    }
}
