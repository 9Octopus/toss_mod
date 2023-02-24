package net.nineocto.tossmod.util.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final String KEY_TOSS = "Drop: Toss Held Item";
    public static KeyMapping tossKeyMapping;
    public static void init(RegisterKeyMappingsEvent event) {
        tossKeyMapping = new KeyMapping(KEY_TOSS, KeyConflictContext.IN_GAME,
        InputConstants.getKey("key.keyboard.tab"), KeyMapping.CATEGORY_INVENTORY);
        event.register(tossKeyMapping);
    }
}
