package net.nineocto.tossmod.util.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TossC2SPacket {
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
