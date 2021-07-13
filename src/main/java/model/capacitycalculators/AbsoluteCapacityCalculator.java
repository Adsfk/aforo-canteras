package model.capacitycalculators;

import model.CapacityCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class AbsoluteCapacityCalculator implements CapacityCalculator {

    private final int max;

    public AbsoluteCapacityCalculator(int max) {
        this.max = max;
    }

    @Override
    public List<Integer> calculate(List<Integer> areas) {
        return areas.stream().mapToInt(a->max).boxed().collect(Collectors.toList());
    }
}
