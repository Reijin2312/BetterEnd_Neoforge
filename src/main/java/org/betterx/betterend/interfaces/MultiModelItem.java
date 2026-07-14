package org.betterx.betterend.interfaces;

import org.betterx.betterend.registry.EndItems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface MultiModelItem {
    @Environment(EnvType.CLIENT)
    void registerModelPredicate();

    static void register() {
        EndItems.getModItems().forEach(item -> {
            if (item instanceof MultiModelItem) {
                ((MultiModelItem) item).registerModelPredicate();
            }
        });
    }
}
