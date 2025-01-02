package dev.uint0.notquitehardcore

import org.slf4j.LoggerFactory
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity

object NotQuiteHardcore : ModInitializer {
    private val logger = LoggerFactory.getLogger("notquitehardcore")

	override fun onInitialize() {
		logger.info("Initializing Not Quite Hardcore")

		ServerLivingEntityEvents.ALLOW_DEATH.register(this::onPlayerDeath)
	}

	private fun onPlayerDeath(entity: Entity, source: DamageSource, damageAmount: Float): Boolean {
        if (entity is ServerPlayerEntity) {
            entity.getInventory().clear();
        }
        return true;
    }
}