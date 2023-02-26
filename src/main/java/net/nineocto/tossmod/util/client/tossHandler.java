package net.nineocto.tossmod.util.client;

import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
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
        double throwDistance = 1.0; // adjust as needed
        double throwAngle = 10.0; // adjust as needed
        double throwHeight = 0.5; // adjust as needed

        double xRot = Math.toRadians(playerRotation.x);
        double yRot = Math.toRadians(playerRotation.y);

        double x = -Math.sin(yRot) * Math.cos(xRot);
        double y = -Math.sin(xRot);
        double z = Math.cos(yRot) * Math.cos(xRot);

        double horizontalDistance = Math.sqrt(x * x + z * z);
        double horizontalVelocity = throwDistance / horizontalDistance;
        double verticalVelocity = throwHeight / (throwDistance * Math.sin(Math.toRadians(throwAngle)) + throwHeight);

        Vec3 motion = new Vec3(x * horizontalVelocity, y * verticalVelocity, z * horizontalVelocity);
        ItemEntity thrownItem = new ItemEntity(player.level, playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()) - 0.2, playerPos.z, thrownStack);
        thrownItem.setDeltaMovement(motion);

        float yaw = (float) Math.toDegrees(Math.atan2(-x, z));
        float pitch = (float) Math.toDegrees(Math.atan2(-y, horizontalDistance));
        thrownItem.setYRot(yaw);
        thrownItem.setXRot(pitch);
        thrownItem.setPickUpDelay(20);
        player.level.addFreshEntity(thrownItem);
    }

    public static void Tossed(ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        while (tossKeyMapping.consumeClick()) {
            ItemStack stackInHand = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
            tossmodPacketHandler.sendToServer(new TossC2SPacket(stackInHand));
            stackInHand.shrink(1);
            System.out.println("WorkingWTF?");
        }
    }
}

