package model.capacitycalculators;

import model.CapacityCalculator;
import model.Restriction;

import java.util.List;

public class RealCapacityCalculator implements CapacityCalculator {
    private List<Restriction> restrictions;

    public RealCapacityCalculator(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public List<Integer> calculate(List<Integer> areas){
        return restrictions.stream()
                .filter(Restriction::isActive)
                .filter(restriction -> restriction.getLimitantTo().equals("FCCR"))
                .map(Restriction::getCapacityCalculator)
                .map(c-> c.calculate(areas))
                .reduce(new PhysicalCapacityCalculator().calculate(areas), this::min);
    }

    private List<Integer> min(List<Integer> min, List<Integer> item) {
        for (int i = 0; i < min.size(); i++)
            min.set(i, min.get(i) < item.get(i) ? min.get(i) : item.get(i));
        return min;
    }
}
