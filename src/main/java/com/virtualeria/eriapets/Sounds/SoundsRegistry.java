package com.virtualeria.eriapets.Sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundsRegistry {
    public static final Identifier SPUMACOCA_ID = new Identifier("eriapets:spumacoca");
    public static SoundEvent SPUMACOCA_EVENT = new SoundEvent(SPUMACOCA_ID);
    public static final Identifier EXPLOSIONBIG_ID = new Identifier("eriapets:explosionbig");
    public static SoundEvent EXPLOSIONBIG_EVENT = new SoundEvent(EXPLOSIONBIG_ID);

    public static void initialize(){
        Registry.register(Registry.SOUND_EVENT, SPUMACOCA_ID, SPUMACOCA_EVENT);
        Registry.register(Registry.SOUND_EVENT, EXPLOSIONBIG_ID, EXPLOSIONBIG_EVENT);
    }

}


