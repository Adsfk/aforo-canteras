package model.capacitycalculators;

import model.CapacityCalculator;
import model.Restriction;

import java.util.List;

public class EffectiveCapacityCalculator implements CapacityCalculator{
    private final List<Restriction> restrictions;

    public EffectiveCapacityCalculator(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public List<Integer> calculate(List<Integer> areas) {
        return restrictions.stream()
                .filter(Restriction::isActive)
                .filter(restriction -> restriction.getLimitantTo().equals("FCCE"))
                .map(Restriction::getCapacityCalculator)
                .map(c-> c.calculate(areas))
                .reduce(new RealCapacityCalculator(restrictions).calculate(areas), this::min);
    }

    private List<Integer> min(List<Integer> min, List<Integer> item) {
        for (int i = 0; i < min.size(); i++)
            min.set(i, min.get(i) < item.get(i) ? min.get(i) : item.get(i));
        return min;
    }
}
