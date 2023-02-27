package net.nineocto.tossmod.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.nineocto.tossmod.util.network.packet.TossC2SPacket;
import net.nineocto.tossmod.util.network.tossmodPacketHandler;

import static net.nineocto.tossmod.util.client.KeyBindings.tossKeyMapping;

//This tells the client to send a packet to the server
public class tossHandler {
    public static void Tossed(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        while (tossKeyMapping.consumeClick()) {
            ItemStack stackInHand = Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND);
            tossmodPacketHandler.sendToServer(new TossC2SPacket(stackInHand));
        }
    }
}