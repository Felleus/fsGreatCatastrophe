package com.fsd.listeners

import net.kyori.adventure.text.Component
import org.bukkit.World
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import com.fsd.FsGreatCatastrophe
import kotlin.random.Random

class PeacefulRevengeListener(private val plugin: FsGreatCatastrophe) : Listener {

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        if (!plugin.isRevengeEnabled()) return

        val entity = event.entity
        val damager = event.damager

        if (entity is Animals && damager is Player) {
            val world: World = entity.world

            if (Random.nextDouble(100.0) < 30.0) { // 30% шанс
                when (Random.nextInt(3)) {
                    0 -> spawnVindicator(world, entity.location)
                    1 -> spawnChargedCreeper(world, entity.location)
                    2 -> spawnSilverfish(world, entity.location)
                }
            }
        }
    }

    private fun spawnVindicator(world: World, location: org.bukkit.Location) {
        val vindicator = world.spawn(location, Vindicator::class.java)
        vindicator.customName(Component.text("§cМстительный Дровосек"))
        vindicator.isCustomNameVisible = true
    }

    private fun spawnChargedCreeper(world: World, location: org.bukkit.Location) {
        val creeper = world.spawn(location, Creeper::class.java)
        creeper.isPowered = true
        creeper.customName(Component.text("§eРазъярённый Крипер"))
        creeper.isCustomNameVisible = true
    }

    private fun spawnSilverfish(world: World, location: org.bukkit.Location) {
        val silverfish = world.spawn(location, Silverfish::class.java)
        silverfish.customName(Component.text("§7Мстительная Чешуйница"))
        silverfish.isCustomNameVisible = true
    }
}
