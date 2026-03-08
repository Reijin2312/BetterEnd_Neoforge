package org.betterx.betterend.interfaces;

import org.betterx.betterend.registry.EndItems;

import net.neoforged.api.distmarker.Dist;

public interface MultiModelItem {
    void registerModelPredicate();

    static void register() {
        EndItems.getModItems().forEach(item -> {
            if (item instanceof MultiModelItem) {
                ((MultiModelItem) item).registerModelPredicate();
            }
        });
    }
}
