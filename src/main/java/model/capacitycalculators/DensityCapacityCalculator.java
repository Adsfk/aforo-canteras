package model.capacitycalculators;

import model.CapacityCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class DensityCapacityCalculator implements CapacityCalculator {

    private final double density;

    public DensityCapacityCalculator(double density) {
        this.density = density;
    }

    @Override
    public List<Integer> calculate(List<Integer> areas) {
        return areas.stream().mapToInt(a-> (int) (a*density)).boxed().collect(Collectors.toList());
    }

    public double getDensity() {
        return density;
    }

}
