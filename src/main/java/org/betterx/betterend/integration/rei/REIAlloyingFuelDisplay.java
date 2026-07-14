package org.betterx.betterend.integration.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.Collections;
import java.util.List;

public class REIAlloyingFuelDisplay extends BasicDisplay {
    private final int fuelTime;

    public REIAlloyingFuelDisplay(List<EntryIngredient> fuel, int fuelTime) {
        super(fuel, Collections.emptyList());
        this.fuelTime = fuelTime;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REIPlugin.ALLOYING_FUEL;
    }

    public int getFuelTime() {
        return fuelTime;
    }

}
