package net.nineocto.tossmod.util.network.packet;

import com.mojang.math.Vector3d;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

//This class executes the code on the server and actually drops the item
public class TossC2SPacket {
    private final ItemStack thrownStack;
    public TossC2SPacket(ItemStack thrownStack) {
        this.thrownStack = thrownStack;
    }
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeItem(thrownStack);
    }
    //This is where the packet is sent to the server
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer sender = context.getSender();
        if (sender == null) {
            return;
        }
        context.enqueueWork(() -> execute(sender));
        context.setPacketHandled(true);
    }
    //This drops the item in the direction the player is looking and removes it from the inventory
    public static void execute(Player player) {
        ItemStack stackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stackInHand.isEmpty()) {
            return;
        }
        ItemStack thrownStack = stackInHand.copy();
        thrownStack.setCount(1);
        //physics for tossing
        Vector3d playerPos = new Vector3d(player.getX(), player.getY(), player.getZ());
        Vec3 lookVec = player.getLookAngle();
        Vec3 motion = new Vec3(lookVec.x, lookVec.y, lookVec.z).normalize().scale(1.0);
        //Tossing and removing from inventory
        ItemEntity thrownItem;
        if (stackInHand.getCount() > 1) {
            thrownItem = new ItemEntity(player.level, playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()) - 0.2, playerPos.z, thrownStack);
            stackInHand.shrink(1);
        } else {
            thrownItem = new ItemEntity(player.level, playerPos.x, playerPos.y + player.getEyeHeight(player.getPose()) - 0.2, playerPos.z, stackInHand);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
        thrownItem.setDeltaMovement(motion);
        thrownItem.setDefaultPickUpDelay();
        player.level.addFreshEntity(thrownItem);
    }
}




