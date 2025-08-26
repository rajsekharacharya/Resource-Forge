package com.app.resourceforge.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepreciationEntry {

    private Integer period;
    private String yearCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private double initialValue;
    private double depreciation;
    private double finalValue;
    private double accumulated;

}
