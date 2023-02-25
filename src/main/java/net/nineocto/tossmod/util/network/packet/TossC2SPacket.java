package net.nineocto.tossmod.util.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

/*public class TossC2SPacket {
    public TossC2SPacket() {

    }
    public TossC2SPacket(FriendlyByteBuf buf) {

    }
    public void toBytes(FriendlyByteBuf buf) {

    }

    //Server commands
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ItemStack held = context.getSender().getMainHandItem();
            player.drop(held, true);
            //.extractItem(0,1,true);
        });
        return true;
    }
}

 */

public class TossC2SPacket {
    private final ItemStack thrownStack;

    public TossC2SPacket(ItemStack thrownStack) {
        this.thrownStack = thrownStack;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeItem(thrownStack);
    }

    public static TossC2SPacket fromBytes(FriendlyByteBuf buffer) {
        ItemStack thrownStack = buffer.readItem();
        return new TossC2SPacket(thrownStack);
    }

    public void handle(NetworkEvent.Context context) {
        context.getSender().getServer().execute(() -> {
            // Perform server-side handling of the packet here
        });
    }
}


