package org.betterx.datagen.betterend;

import org.betterx.bclib.api.v3.datagen.RegistrySupplier;
import org.betterx.betterend.BetterEnd;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class EndRegistrySupplier extends RegistrySupplier {
    public static final EndRegistrySupplier INSTANCE = new EndRegistrySupplier();

    protected EndRegistrySupplier() {
        super(List.of(BetterEnd.MOD_ID));
    }

    @Override
    protected List<RegistryInfo<?>> initializeRegistryList(@Nullable List<String> modIDs) {
        return List.of(

        );
    }
}
