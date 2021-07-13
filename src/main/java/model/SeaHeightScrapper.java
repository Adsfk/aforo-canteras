package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SeaHeightScrapper {
    private List<Double> currentHoursMeasures;
    private LocalDateTime currentTime;
    private static final double NUMBER_OF_DIGITS = 1000d;

    private static SeaHeightScrapper seaHeightScrapper;

    private SeaHeightScrapper() {
        this.currentHoursMeasures = new ArrayList<>();
        this.currentTime = getCurrentTime();
    }

    public static SeaHeightScrapper getInstance() {
        if (seaHeightScrapper == null)
            seaHeightScrapper = new SeaHeightScrapper();
        return seaHeightScrapper;
    }

    public void logTide(double tide) {
        if(currentHoursMeasures.size() == 60 || currentTime.isBefore(getCurrentTime())){
            currentHoursMeasures.clear();
        }
        currentHoursMeasures.add(tide);
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }

    public double getLastTide() {
        return currentHoursMeasures.get(currentHoursMeasures.size()-1);
    }

    public double getAverageHeight() {
        double average = getAverage();
        return round(average);
    }

    private double getAverage() {
        return currentHoursMeasures.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(-40);
    }

    private static double round(double number) {
        return (double) Math.round(number * NUMBER_OF_DIGITS) / NUMBER_OF_DIGITS;
    }
}
