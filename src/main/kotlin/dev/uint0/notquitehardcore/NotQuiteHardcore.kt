package dev.uint0.notquitehardcore

import org.slf4j.LoggerFactory
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.loader.impl.FabricLoaderImpl
import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.item.Items
import net.minecraft.world.GameRules

object NotQuiteHardcore : ModInitializer {
    private val logger = LoggerFactory.getLogger("notquitehardcore")

    val GR_CLEAR_INVENTORY_ON_DEATH = GameRuleRegistry.register(
        "nqhClearInventoryOnDeath",
        GameRules.Category.PLAYER,
        GameRuleFactory.createBooleanRule(false)
    );


	override fun onInitialize() {
        logger.info("Initializing Not Quite Hardcore")

        ServerLivingEntityEvents.ALLOW_DEATH.register(this::onPlayerDeath)
	}

	private fun onPlayerDeath(entity: LivingEntity, source: DamageSource, damageAmount: Float): Boolean {
        if (entity is ServerPlayerEntity) {
            val world = entity.getWorld() as? ServerWorld ?: return true

            if(!world.getGameRules().getBoolean(GR_CLEAR_INVENTORY_ON_DEATH)) {
                return true;
            }
            if (entity.getInventory().contains(Items.TOTEM_OF_UNDYING.getDefaultStack())) {
                return true;
            }

            entity.getInventory().clear();
        }

        return true;
    }
}