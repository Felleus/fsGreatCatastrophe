package com.fsd.config

import com.fsd.FsGreatCatastrophe
import org.bukkit.configuration.file.FileConfiguration

object ConfigManager {

    private lateinit var config: FileConfiguration

    fun initialize(plugin: FsGreatCatastrophe) {
        config = plugin.config
    }

    fun getRevengeDuration(): Int {
        return config.getInt("revenge.duration", 60)
    }


    fun getRainDuration(): Int {
        return config.getInt("rain.duration", 60)
    }

    fun getRainStartMessage(): String {
        return config.getString("rain.startMessage", "<yellow>⚠ Начался кислотный дождь! Длительность: %duration% секунд.")!!
    }

    fun getRainEndMessage(): String {
        return config.getString("rain.endMessage", "<green>☀ Кислотный дождь закончился.")!!
    }

    fun getLightningDuration(): Int {
        return config.getInt("lightning.duration", 30)
    }

    fun getLightningIntensity(): Int {
        return config.getInt("lightning.intensity", 5)
    }

    fun getLightningRadius(): Int {
        return config.getInt("lightning.radius", 30)
    }

    fun getLightningStartMessage(): String {
        return config.getString("lightning.startMessage", "<yellow>⚡ Начался шторм молний! Длительность: %duration% секунд.")!!
    }

    fun getLightningEndMessage(): String {
        return config.getString("lightning.endMessage", "<gray>🌩 Шторм молний завершен.")!!
    }
    fun getRevengeStartMessage(duration: Int): String {
        return config.getString("revenge.startMessage", "<green>Месть мирных животных активирована на <yellow>%duration% <green>секунд.")
            ?.replace("%duration%", duration.toString()) ?: ""
    }

    fun getRevengeAlreadyActiveMessage(): String {
        return config.getString("revenge.alreadyActiveMessage", "<red>Месть мирных животных уже активирована!") ?: ""
    }

    fun getRevengeEndMessage(): String {
        return config.getString("revenge.endMessage", "<red>Месть мирных животных отключена.") ?: ""
    }

}
