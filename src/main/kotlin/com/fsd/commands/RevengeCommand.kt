package com.fsd.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Description
import com.fsd.FsGreatCatastrophe
import com.fsd.config.ConfigManager
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class RevengeCommand(private val plugin: FsGreatCatastrophe) : BaseCommand() {

    private val miniMessage = MiniMessage.miniMessage()

    @CommandAlias("revenge")
    @Description("Активировать месть мирных животных на указанное время из конфига")
    fun onRevengeCommand(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage("§cЭту команду могут использовать только игроки.")
            return
        }

        val duration = ConfigManager.getRevengeDuration()

        if (plugin.isRevengeEnabled()) {
            val alreadyActiveMessage = ConfigManager.getRevengeAlreadyActiveMessage()
            sender.sendMessage(miniMessage.deserialize(alreadyActiveMessage))
        } else {
            val startMessage = ConfigManager.getRevengeStartMessage(duration)
            sender.sendMessage(miniMessage.deserialize(startMessage))
            plugin.setRevengeEnabled(true)

            object : BukkitRunnable() {
                override fun run() {
                    plugin.setRevengeEnabled(false)
                    val endMessage = ConfigManager.getRevengeEndMessage()
                    sender.sendMessage(miniMessage.deserialize(endMessage))
                }
            }.runTaskLater(plugin, duration * 20L) // Переводим секунды в тики
        }
    }
}
