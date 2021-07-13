package model.capacitycalculators;

import model.CapacityCalculator;

import java.util.List;

public class PhysicalCapacityCalculator implements CapacityCalculator {
    public static DensityCapacityCalculator physical = new DensityCapacityCalculator(0.25);

    @Override
    public List<Integer> calculate(List<Integer> areas) {
        return physical.calculate(areas);
    }

    public void changeDensity(double densityValue){
        physical = new DensityCapacityCalculator(densityValue);
    }

    public double getDensity() {
        return physical.getDensity();
    }
}
