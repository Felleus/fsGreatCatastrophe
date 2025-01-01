package com.fsd.disasters

import com.fsd.FsGreatCatastrophe
import com.fsd.config.ConfigManager
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class LightningStorm(private val plugin: FsGreatCatastrophe) {

    private val miniMessage = MiniMessage.miniMessage()
    private val legacySerializer = LegacyComponentSerializer.legacySection()

    fun startLightningStorm(world: World) {
        val duration = ConfigManager.getLightningDuration()
        val intensity = ConfigManager.getLightningIntensity()
        val radius = ConfigManager.getLightningRadius()

        val startMessageRaw = ConfigManager.getLightningStartMessage().replace("%duration%", duration.toString())
        val endMessageRaw = ConfigManager.getLightningEndMessage()

        val startMessage = legacySerializer.serialize(miniMessage.deserialize(startMessageRaw))
        val endMessage = legacySerializer.serialize(miniMessage.deserialize(endMessageRaw))

        world.players.forEach { player ->
            player.sendMessage(startMessage)
        }

        object : BukkitRunnable() {
            var timeLeft = duration

            override fun run() {
                if (timeLeft <= 0) {
                    cancel()
                    world.players.forEach { player ->
                        player.sendMessage(endMessage)
                    }
                    return
                }

                world.players.forEach { player ->
                    for (i in 0 until intensity) {
                        val randomLocation = getRandomLocationAroundPlayer(player.location, radius)
                        world.strikeLightning(randomLocation)
                    }
                }

                timeLeft--
            }
        }.runTaskTimer(plugin, 0L, 20L)
    }

    private fun getRandomLocationAroundPlayer(center: Location, radius: Int): Location {
        val world = center.world
        val randomX = center.x + Random.nextDouble(-radius.toDouble(), radius.toDouble())
        val randomZ = center.z + Random.nextDouble(-radius.toDouble(), radius.toDouble())
        val y = world.getHighestBlockYAt(randomX.toInt(), randomZ.toInt()) + 1
        return Location(world, randomX, y.toDouble(), randomZ)
    }
}
