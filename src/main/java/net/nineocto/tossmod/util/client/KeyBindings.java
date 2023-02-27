package net.nineocto.tossmod.util.client;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

import static net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

//This simply registers our keybind, It is triggered in Client Events.
public class KeyBindings {
    public static final String KEY_TOSS = "Drop Toss Held Item";
   public static KeyMapping tossKeyMapping;
        @OnlyIn(Dist.CLIENT)
        public static void init(RegisterKeyMappingsEvent event) {
            tossKeyMapping = new KeyMapping(
                    KEY_TOSS, IN_GAME, Type.KEYSYM, GLFW_KEY_TAB, "key.categories.inventory"
            );
            event.register(tossKeyMapping);
        }
}