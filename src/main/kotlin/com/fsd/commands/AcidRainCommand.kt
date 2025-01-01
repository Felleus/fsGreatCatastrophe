package com.fsd.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Description
import com.fsd.FsGreatCatastrophe
import com.fsd.disasters.RainDisaster
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AcidRainCommand(private val plugin: FsGreatCatastrophe) : BaseCommand() {

    private val rainDisaster = RainDisaster(plugin)

    @CommandAlias("acidrain")
    @CommandCompletion("[длительность в сек.]")
    @Description("Вызвать кислотный дождь")
    fun onAcidRainCommand(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage("Эта команда доступна только игрокам!")
            return
        }

        val world = sender.world
        rainDisaster.startAcidRain(world)
    }
}
