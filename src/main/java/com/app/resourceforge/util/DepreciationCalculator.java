package com.app.resourceforge.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.app.resourceforge.DTO.DepreciationEntry;

public class DepreciationCalculator {

    private double initialCost;
    private double salvageValue;
    private int usefulLife;
    private LocalDate purchaseDate;

    public DepreciationCalculator(double initialCost, double salvageValue, int usefulLife, LocalDate purchaseDate) {
        this.initialCost = initialCost;
        this.salvageValue = salvageValue;
        this.usefulLife = usefulLife;
        this.purchaseDate = purchaseDate;
    }

    public List<DepreciationEntry> calculateStraightLineDepreation() {
        List<DepreciationEntry> schedule = new ArrayList<>();
        double depreciationExpense = (initialCost - salvageValue) / (usefulLife * 365); // Depreciation per day
    
        double pickFinal = initialCost;
        LocalDate dt = purchaseDate;
        double totalDep = 0.0;
    
        int totalDays = usefulLife * 365;
        int usedDays = 0;
        int yearPeriod = 1;
    
        while (usedDays < totalDays) {
            if (totalDays - usedDays >= 365) {
                LocalDate startDate = dt;
                LocalDate endDate = dt;

                int yr = startDate.getYear();
                Month startMonth = startDate.getMonth();

                if (startMonth.compareTo(Month.MARCH) <= 0) {
                    endDate = LocalDate.of(yr, Month.MARCH, 31);
                } else {
                    endDate = LocalDate.of(yr + 1, Month.MARCH, 31);
                }

                // endDate = dt.plusYears(1).minusDays(1); // End date is one year from current date minus one day
    
                long daysDifference = ChronoUnit.DAYS.between(dt, endDate) + 1;
                usedDays += (int) daysDifference;
    
                double initial = pickFinal;
                double depreciation = depreciationExpense * daysDifference;
                double finalValue = initial - depreciation;
                totalDep += depreciation;
                String yearCode = extractFinancialYearCode(dt, endDate);
    
                schedule.add(new DepreciationEntry(yearPeriod, yearCode, startDate, endDate, initial, depreciation, finalValue, totalDep));
    
                pickFinal = finalValue;
                dt = endDate.plusDays(1);
            } else {
                int remainingDays = totalDays - usedDays;
                LocalDate startDate = dt;
                LocalDate endDate = dt.plusDays(remainingDays);
                usedDays += remainingDays;
    
                double initial = pickFinal;
                double depreciation = depreciationExpense * remainingDays;
                double finalValue = initial - depreciation;
                totalDep += depreciation;
    
                String yearCode = extractFinancialYearCode(dt, endDate);
    
                schedule.add(new DepreciationEntry(yearPeriod, yearCode, startDate, endDate, initial, depreciation, finalValue, totalDep));
    
                pickFinal = finalValue;
                dt = endDate.plusDays(1);
            }
    
            yearPeriod++;
        }
        return schedule;
    }

    public List<DepreciationEntry> calculateWDVDepreation() {
        List<DepreciationEntry> schedule = new ArrayList<>();
    
        double depreciationRate = calculateDepreciationRate(salvageValue, initialCost, usefulLife);
        double pickFinal = initialCost;
        LocalDate dt = purchaseDate;
        double totalDep = 0.0;
    
        int totalDays = usefulLife * 365;
        int usedDays = 0;
        int yearPeriod = 1;
    
        while (usedDays < totalDays) {
            if (totalDays - usedDays >= 365) {
                LocalDate startDate = dt;
                LocalDate endDate = dt;

                int yr = startDate.getYear();
                Month startMonth = startDate.getMonth();

                if (startMonth.compareTo(Month.MARCH) <= 0) {
                    endDate = LocalDate.of(yr, Month.MARCH, 31);
                } else {
                    endDate = LocalDate.of(yr + 1, Month.MARCH, 31);
                }
                long daysDifference = ChronoUnit.DAYS.between(dt, endDate) + 1;
                usedDays += (int) daysDifference;
                double initial = pickFinal;
                double depreciation = (initial * depreciationRate * daysDifference) / 365.0; // Adjusted for daily basis
                double finalValue = initial - depreciation;
                totalDep += depreciation;
                String yearCode = extractFinancialYearCode(startDate, endDate);
                schedule.add(new DepreciationEntry(yearPeriod, yearCode, startDate, endDate, initial, depreciation,
                        finalValue,
                        totalDep));
                pickFinal = finalValue;
                dt = endDate.plusDays(1);
            } else {
                int remainingDays = totalDays - usedDays;
                LocalDate startDate = dt;
                LocalDate endDate = dt.plusDays(remainingDays);
                usedDays += remainingDays;
                double initial = pickFinal;
                double depreciation = (initial * depreciationRate * remainingDays) / 365.0; // Adjusted for daily basis
                double finalValue = initial - depreciation;
                totalDep += depreciation;
                String yearCode = extractFinancialYearCode(dt, endDate);
                schedule.add(new DepreciationEntry(yearPeriod, yearCode, startDate, endDate, initial, depreciation,
                        finalValue,
                        totalDep));
                pickFinal = finalValue;
                dt = endDate.plusDays(1);
            }
    
            yearPeriod++;
        }
    
        return schedule;
    }

    public static double calculateDepreciationRate(double s, double c, double n) {
        return 1 - Math.pow((s / c), 1 / n);
    }

    public static String extractFinancialYearCode(LocalDate startDate, LocalDate endDate) {
        int year = startDate.getYear();
        boolean isBeforeApril = startDate.getMonthValue() < Month.APRIL.getValue();

        if (year == endDate.getYear()) {
            if (isBeforeApril) {
                return (year - 1) + "-" + String.format("%02d", year % 100);
            } else {
                return year + "-" + String.format("%02d", (year + 1) % 100);
            }
        } else {
            return year + "-" + String.format("%02d", endDate.getYear() % 100);
        }
    }

}
