package org.betterx.betterend.mixin.client;

import org.betterx.betterend.config.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicTrackerMixin {
    @Unique private static final float FADE_SPEED = 0.2f;
    @Unique private static final float TICK_DELTA = 0.05f;
    @Unique private final MusicManager be_thisObj = (MusicManager) (Object) this;
    @Unique private boolean be_waitChange;
    @Unique private float be_volume = 1.0f;

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private RandomSource random;
    @Shadow private SoundInstance currentMusic;
    @Shadow private int nextSongDelay;

    @Unique
    private boolean be_isInEnd() {
        return minecraft.player != null && minecraft.level != null && minecraft.level.dimension() == Level.END;
    }

    @Unique
    private boolean be_shouldChangeMusic(Music toMusic) {
        return currentMusic == null || !toMusic.getEvent().value().getLocation().equals(currentMusic.getLocation());
    }

    @Unique
    private float be_getVolumeSafe(float fallback) {
        if (currentMusic == null) return fallback;
        try {
            return currentMusic.getVolume();
        } catch (NullPointerException ignored) {
            return fallback;
        }
    }

    @Inject(method = "startPlaying", at = @At("TAIL"))
    private void be_startPlaying(Music music, CallbackInfo info) {
        be_volume = 0.0f;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void be_onTick(CallbackInfo info) {
        if (!Configs.CLIENT_CONFIG.blendBiomeMusic.get() || !be_isInEnd()) {
            be_waitChange = false;
            be_volume = 1.0f;
            return;
        }

        Music targetMusic = minecraft.getSituationalMusic();
        if (targetMusic == null || !targetMusic.replaceCurrentMusic()) {
            be_waitChange = false;
            be_volume = 1.0f;
            return;
        }

        if (currentMusic != null && !minecraft.getSoundManager().isActive(currentMusic)) {
            currentMusic = null;
            nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, targetMusic.getMinDelay(), targetMusic.getMaxDelay()));
        }
        nextSongDelay = Math.min(nextSongDelay, targetMusic.getMaxDelay());

        if (currentMusic == null) {
            if (nextSongDelay-- <= 0) {
                be_waitChange = false;
                be_thisObj.startPlaying(targetMusic);
                be_setCurrentMusicVolume(0.0f);
            }
            info.cancel();
            return;
        }

        boolean volumeChanged = false;
        if (be_waitChange || be_shouldChangeMusic(targetMusic)) {
            if (!be_waitChange) {
                nextSongDelay = random.nextInt(0, Math.max(targetMusic.getMinDelay() / 2, 1));
                be_waitChange = true;
            }
            if (be_volume > 0.0f) {
                volumeChanged = true;
                be_volume -= FADE_SPEED * TICK_DELTA;
                if (be_volume <= 0.0f) {
                    be_volume = 0.0f;
                    minecraft.getSoundManager().stop(currentMusic);
                    currentMusic = null;
                }
            } else if (nextSongDelay > 0) {
                nextSongDelay--;
            } else {
                be_waitChange = false;
                be_thisObj.startPlaying(targetMusic);
                be_setCurrentMusicVolume(0.0f);
            }
        } else if (be_volume < 1.0f) {
            volumeChanged = true;
            be_volume += FADE_SPEED * TICK_DELTA;
        }

        if (volumeChanged) {
            be_volume = Mth.clamp(be_volume, 0.0f, 1.0f);
            be_setCurrentMusicVolume(be_volume);
        }

        info.cancel();
    }

    @Unique
    private void be_setCurrentMusicVolume(float volume) {
        if (currentMusic instanceof AbstractSoundInstanceAccessor accessor) {
            accessor.setVolume(volume);
            minecraft.getSoundManager().updateSourceVolume(currentMusic.getSource(), be_getVolumeSafe(volume));
        }
    }
}
