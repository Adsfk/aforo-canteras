package model;

import model.capacitycalculators.AbsoluteCapacityCalculator;
import model.capacitycalculators.DensityCapacityCalculator;

public class CapacityCalculatorFactory {

    public CapacityCalculator build(String name, Double value){
        if(name.equals("Density"))
            return new DensityCapacityCalculator(value);
        if(name.equals("Absolute"))
            return new AbsoluteCapacityCalculator((int) value.doubleValue());
        return null;
    }
}
