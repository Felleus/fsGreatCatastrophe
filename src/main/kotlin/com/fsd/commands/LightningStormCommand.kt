package com.fsd.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Description
import com.fsd.FsGreatCatastrophe
import com.fsd.disasters.LightningStorm
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LightningStormCommand(private val plugin: FsGreatCatastrophe) : BaseCommand() {

    private val lightningStorm = LightningStorm(plugin)

    @CommandAlias("lightningstorm")
    @CommandCompletion("[длительность в сек.]")
    @Description("Вызвать шторм молний")
    fun onLightningStormCommand(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage("Эта команда доступна только игрокам!")
            return
        }

        val world = sender.world
        val intensity = plugin.config.getInt("lightning.intensity", 5)
        lightningStorm.startLightningStorm(world)
    }
}
