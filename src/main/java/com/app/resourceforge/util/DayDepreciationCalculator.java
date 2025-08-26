package com.app.resourceforge.util;

import java.time.LocalDate;

public class DayDepreciationCalculator {

    public static double calculateStraightLine(double purchaseValue, double salvageValue, int usefulLife, LocalDate purchaseDate,
            LocalDate today) {

        // Calculate the annual depreciation expense
        double perDayDepreciation = (purchaseValue - salvageValue) / (usefulLife * 365);

        System.out.println(perDayDepreciation);

        int daysInService = (int) today.toEpochDay() - (int) purchaseDate.toEpochDay();

        // Calculate the accumulated depreciation
        double accumulatedDepreciation = perDayDepreciation * daysInService;

        System.out.println(accumulatedDepreciation);

        // Calculate the asset value
        double assetValue = purchaseValue - accumulatedDepreciation;

        System.out.println(assetValue);

        return assetValue;
    }

    public static double calculateWDV(double purchaseValue, double salvageValue, int usefulLife, LocalDate purchaseDate,
            LocalDate today) {
        // Calculate the depreciation rate.
        double depreciationRate = (purchaseValue - salvageValue) / usefulLife;

        // Calculate the number of days that the asset has been in service since the
        // beginning of the year.
        int daysInService = (int) today.toEpochDay() - (int) purchaseDate.toEpochDay();

        // Prorate the depreciation for the year.
        double proratedDepreciation = depreciationRate * daysInService / 365.25;

        // Calculate the WDV of the asset on the given date.
        double wdv = purchaseValue - proratedDepreciation;

        return wdv;
        
    }

}
