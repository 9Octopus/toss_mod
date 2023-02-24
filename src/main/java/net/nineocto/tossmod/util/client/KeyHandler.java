package net.nineocto.tossmod.util.client;

import net.minecraftforge.event.TickEvent;

import static net.nineocto.tossmod.util.client.KeyBindings.tossKeyMapping;

public class KeyHandler {
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            while (tossKeyMapping.consumeClick()) {
                System.out.println("WORKS!");
            }
        }
    }
}
