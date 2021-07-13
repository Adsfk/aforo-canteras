package model.interpolation;

import java.util.function.Function;

public class Interpolation {
    private static final double NUMBER_OF_DECIMALS = 1000d;
    private final double x1;
    private final double x2;
    private final double y1;
    private final double y2;

    public Interpolation(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Function<Double, Double> calculate() {
        double div = (y2-y1)/(x2-x1);
        return x -> (double) Math.round((((y1 + div * (x - x1)) * NUMBER_OF_DECIMALS) / NUMBER_OF_DECIMALS) *
                NUMBER_OF_DECIMALS) / NUMBER_OF_DECIMALS;
    }
}
