package model;

import java.util.List;

@FunctionalInterface
public interface CapacityCalculator {
    List<Integer> calculate(List<Integer> areas);
}
