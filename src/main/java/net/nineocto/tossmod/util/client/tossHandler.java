package net.nineocto.tossmod.util.client;

import net.minecraftforge.event.TickEvent;
import net.nineocto.tossmod.util.network.packet.TossC2SPacket;
import net.nineocto.tossmod.util.network.tossmodPacketHandler;

import static net.nineocto.tossmod.util.client.KeyBindings.tossKeyMapping;

public class tossHandler {
    public static void Tossed(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (tossKeyMapping.consumeClick()) {
                System.out.println("Click");
                tossmodPacketHandler.sendToServer(new TossC2SPacket());
            }

        }
    }
}

