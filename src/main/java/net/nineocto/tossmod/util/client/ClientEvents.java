package net.nineocto.tossmod.util.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

//This tells the server to listen
public class ClientEvents  {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyBindRegister(RegisterKeyMappingsEvent event) {
            KeyBindings.init(event);
        }
        @SubscribeEvent
        public static void init(FMLClientSetupEvent event) {
             MinecraftForge.EVENT_BUS.addListener(tossHandler::Tossed);
        }
    }
}
