package com.fsd.disasters

import com.fsd.FsGreatCatastrophe
import com.fsd.config.ConfigManager
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class RainDisaster(private val plugin: FsGreatCatastrophe) {

    private val miniMessage = MiniMessage.miniMessage()
    private val legacySerializer = LegacyComponentSerializer.legacySection()

    fun startAcidRain(world: World) {
        val duration = ConfigManager.getRainDuration()

        val startMessageRaw = ConfigManager.getRainStartMessage().replace("%duration%", duration.toString())
        val endMessageRaw = ConfigManager.getRainEndMessage()

        val startMessage = legacySerializer.serialize(miniMessage.deserialize(startMessageRaw))
        val endMessage = legacySerializer.serialize(miniMessage.deserialize(endMessageRaw))

        world.setStorm(true)
        world.weatherDuration = duration * 20

        world.players.forEach { player ->
            player.sendMessage(startMessage)
        }

        object : BukkitRunnable() {
            var timeLeft = duration * 20

            override fun run() {
                if (timeLeft <= 0) {
                    world.setStorm(false)
                    world.players.forEach { player ->
                        player.sendMessage(endMessage)
                    }
                    cancel()
                    return
                }

                world.players.forEach { player ->
                    applyPoisonEffect(player)
                    spawnAcidParticles(player)
                }

                timeLeft -= 20
            }
        }.runTaskTimer(plugin, 0L, 20L)
    }

    private fun applyPoisonEffect(player: Player) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            if (!player.hasPotionEffect(PotionEffectType.POISON)) {
                player.addPotionEffect(PotionEffect(PotionEffectType.POISON, 40, 1, false, false, false))
            }

            if (player.gameMode == GameMode.SURVIVAL || player.gameMode == GameMode.ADVENTURE) {
                val newHealth = (player.health - 1.0).coerceAtLeast(0.0)
                player.health = newHealth
            }
        })
    }

    private fun spawnAcidParticles(player: Player) {
        val world = player.world
        val dustOptions = Particle.DustOptions(Color.LIME, 0.8f)

        val centerX = player.location.x.toInt()
        val centerY = player.location.y.toInt()
        val centerZ = player.location.z.toInt()

        val radius = 20
        val minY = centerY - 10
        val maxY = centerY + 15

        for (x in centerX - radius..centerX + radius) {
            for (z in centerZ - radius..centerZ + radius) {
                for (y in minY..maxY) {
                    if (Math.random() < 0.01) {
                        val location = Location(world, x + 0.5, y + 0.5, z + 0.5)
                        if (world.getBlockAt(location).type == Material.AIR) {
                            world.spawnParticle(
                                Particle.REDSTONE,
                                location,
                                1,
                                dustOptions
                            )
                        }
                    }
                }
            }
        }
    }
}
