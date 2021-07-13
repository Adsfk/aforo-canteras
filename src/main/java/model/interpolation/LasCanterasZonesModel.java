package model.interpolation;

import java.util.*;
import java.util.stream.Collectors;

public class LasCanterasZonesModel {
    public static final double NUMBER_OF_DIGITS = 1000d;
    private static List<int[]> zones = new ArrayList<>();

    public List<Integer> calculateSurfaces(double seaHeight) {
        return zones.stream()
                .map(sectors -> round(calculateSurface(seaHeight, sectors)))
                .collect(Collectors.toList());
    }

    private double calculateSurface(double seaHeight, int[] sectors) {
        return Arrays.stream(sectors)
                .mapToDouble(sector -> calculateSurface(seaHeight, sector))
                .sum();
    }

    private double calculateSurface(double seaHeight, int surface) {
        return new LasCanterasSurfaceModel(surface).surface(seaHeight);
    }

    private int round(double surface) {
        return (int) (Math.round(surface * NUMBER_OF_DIGITS)/NUMBER_OF_DIGITS);
    }

    static {
        zones.add(new int[]{1,2,3,4,5,6});
        zones.add(new int[]{7,8,9,10,11,12});
        zones.add(new int[]{13,14,15,16,17,18});
        zones.add(new int[]{19,20,21,22,23,24});
        zones.add(new int[]{25,26,27,28,29,30,31,32});
    }
}
