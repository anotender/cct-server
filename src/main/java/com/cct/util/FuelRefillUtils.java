package com.cct.util;

import com.cct.model.FuelRefill;

import java.util.Collection;

public final class FuelRefillUtils {

    private FuelRefillUtils() {
    }

    public static double countAverageFuelConsumption(FuelRefill fuelRefill) {
        return fuelRefill.getLiters() * 100 / fuelRefill.getDistance();
    }

    public static double countAverageFuelConsumption(Collection<FuelRefill> fuelRefills) {
        double totalDistance = fuelRefills
                .stream()
                .mapToDouble(FuelRefill::getDistance)
                .sum();

        double totalLiters = fuelRefills
                .stream()
                .mapToDouble(FuelRefill::getLiters)
                .sum();

        return totalLiters * 100 / totalDistance;
    }

}
