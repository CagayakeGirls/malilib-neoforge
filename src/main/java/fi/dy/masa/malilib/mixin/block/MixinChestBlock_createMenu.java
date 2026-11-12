package fi.dy.masa.malilib.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.Minecraft;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import fi.dy.masa.malilib.MaLiLibConfigs;
import fi.dy.masa.malilib.mixin.item.IMixinCompoundContainer;
import fi.dy.masa.malilib.registry.Registry;

@Mixin(targets = "net.minecraft.world.level.block.ChestBlock$2$1", priority = 1001)
public class MixinChestBlock_createMenu
{
//	@Shadow @Final ChestBlockEntity val$first;
//	@Shadow @Final ChestBlockEntity val$second;

	@WrapOperation(
			method = "createMenu(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/inventory/AbstractContainerMenu;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/inventory/ChestMenu;sixRows(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)Lnet/minecraft/world/inventory/ChestMenu;"
			)
	)
	private ChestMenu malilib_onOpenMenu3Post(int containerId, Inventory inventory, Container container, Operation<ChestMenu> original)
	{
		if (MaLiLibConfigs.Generic.ENABLE_CHEST_DATA_TRACKER.getBooleanValue() && container instanceof CompoundContainer)
		{
			//Temporary fix for the @Shadow issues
			ChestBlockEntity first = (ChestBlockEntity) ((IMixinCompoundContainer) container).malilib_getContainer1();
			ChestBlockEntity second = (ChestBlockEntity) ((IMixinCompoundContainer) container).malilib_getContainer2();

			Minecraft.getInstance()
			         .execute(() ->
			                  {
				                  if (container instanceof CompoundContainer)
				                  {
					                  Registry.ENTITY_DATA_REGISTRY.chestTracker().onContainerMenuOpened(containerId, first.getBlockPos(), first);
					                  Registry.ENTITY_DATA_REGISTRY.chestTracker().onContainerMenuOpened(containerId, second.getBlockPos(), first);
				                  }
				                  else
				                  {
					                  Registry.ENTITY_DATA_REGISTRY.chestTracker().onContainerMenuOpened(containerId, first.getBlockPos(), first);
				                  }
			                  });
		}

		return original.call(containerId, inventory, container);
	}
}
