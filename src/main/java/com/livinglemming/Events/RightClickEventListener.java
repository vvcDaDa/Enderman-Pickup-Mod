package com.livinglemming.Events;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EndermanEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResult;

public class RightClickEventListener {
    public static void registerRightClickEvent() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSneaking() && entity instanceof EndermanEntity) {
                EndermanEntity enderman = (EndermanEntity) entity;
                NbtCompound nbt = new NbtCompound();
                enderman.writeCustomDataToNbt(nbt);

                Item spawnEgg = SpawnEggItem.forEntity(enderman.getType());
                if (spawnEgg != null) {
                    ItemStack spawnEggStack = new ItemStack(spawnEgg);
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.put("EntityTag", nbt);
                    spawnEggStack.setNbt(nbtCompound);
                    player.giveItemStack(spawnEggStack);
                }

                enderman.teleport(enderman.getX(), enderman.getY() + 100, enderman.getZ());
                enderman.kill();
            }

            return ActionResult.PASS;
        });
    }
}
