package net.nineocto.tossmod.util.client;

import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.nineocto.tossmod.util.network.packet.TossC2SPacket;
import net.nineocto.tossmod.util.network.tossmodPacketHandler;

import static net.nineocto.tossmod.util.client.KeyBindings.tossKeyMapping;

public class tossHandler {
/*
    public static void execute(Player player) {
        Inventory inventory = player.getInventory();
        ItemStack stackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!stackInHand.isEmpty()) {
            ItemStack thrownStack = stackInHand.copy();
            thrownStack.setCount(1); // only throw one item
            Vector3d playerPos = new Vector3d(player.getX(), player.getY(), player.getZ());
            Vec2 playerRotation = player.getRotationVector();
            ItemEntity thrownItem = new ItemEntity(player.level, playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()), playerPos.z, thrownStack);
            thrownItem.setDeltaMovement(playerRotation.x, playerRotation.y + 0.1D, 0.0);
            thrownItem.setPos(playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()), playerPos.z);
            player.level.addFreshEntity(thrownItem);
            // remove the item from the player's inventory
            stackInHand.shrink(1);
            // send a packet to the server
            tossmodPacketHandler.sendToServer(thrownStack);
        }
    }
    public static void Tossed(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (tossKeyMapping.consumeClick()) {
                System.out.println("Click");
                tossmodPacketHandler.sendToServer(new TossC2SPacket());
            }

        }
    }

 */
    public static void execute(Player player) {
        ItemStack stackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stackInHand.isEmpty()) {
            return;
        }

        ItemStack thrownStack = stackInHand.copy();
        thrownStack.setCount(1);

        Vector3d playerPos = new Vector3d(player.getX(), player.getY(), player.getZ());
        Vec2 playerRotation = player.getRotationVector();
        double throwDistance = 1.5; // tweak this value to adjust how far the item is thrown
        double throwHeight = 0.5; // tweak this value to adjust how high the item is thrown

        ItemEntity thrownItem = new ItemEntity(player.level, playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()) - 0.2, playerPos.z, thrownStack);
        double x = -Math.sin(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
        double y = -Math.sin(Math.toRadians(player.getXRot()));
        double z = Math.cos(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
        thrownItem.setDeltaMovement(x * throwDistance, y * throwHeight, z * throwDistance);
        player.level.addFreshEntity(thrownItem);
    }

    public static void Tossed(ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        while (tossKeyMapping.consumeClick()) {
            Minecraft.getInstance().player.playSound(SoundEvents.INK_SAC_USE, 1.0f, 1.0f);
            ItemStack stackInHand = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
            tossmodPacketHandler.sendToServer(new TossC2SPacket(stackInHand));
            stackInHand.shrink(1);
            System.out.println("WorkingWTF?");
        }
    }
}

