package org.betterx.betterend.events;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface PlayerAdvancementsCallback {

    Event<PlayerAdvancementsCallback> PLAYER_ADVANCEMENT_COMPLETE = EventFactory.createArrayBacked(
            PlayerAdvancementsCallback.class,
            callbacks -> (player, advancement, criterionName) -> {
                for (PlayerAdvancementsCallback event : callbacks) {
                    event.onAdvancementComplete(player, advancement, criterionName);
                }
            }
    );

    void onAdvancementComplete(ServerPlayer player, AdvancementHolder advancement, String criterionName);
}
