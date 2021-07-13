package timer;

import API.controllers.TidesController;
import model.interpolation.LasCanterasZonesModel;
import model.SeaHeightScrapper;
import model.Tides;
import data_scraper.mediciones.TideMeasures;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimerTask extends java.util.TimerTask {
    private final int ZONE_1 = 0;
    private final int ZONE_2 = 1;
    private final int ZONE_3 = 2;
    private final int ZONE_4 = 3;
    private final int ZONE_5 = 4;
    private TideMeasures tideMeasures;
    private LocalDateTime currentTime;
    private SeaHeightScrapper seaHeightScrapper;

    public TimerTask(TideMeasures tideMeasures, SeaHeightScrapper seaHeightScrapper) {
        this.tideMeasures = tideMeasures;
        this.seaHeightScrapper = seaHeightScrapper;
    }

    @Override
    public void run() {
        if (timeNotLogged()) currentTime = getCurrentTime();
        double tide = getTideMeasure();
        if (hoursHasChanged()) persistMeasures();
        if(tide != -40.) logTideMeasure(tide);
    }

    private boolean hoursHasChanged() {
        return currentTime.isBefore(getCurrentTime());
    }

    private boolean timeNotLogged() {
        return currentTime == null;
    }

    private void logTideMeasure(double tide) {
        currentTime = getCurrentTime();
        seaHeightScrapper.logTide(tide);
    }

    private double getTideMeasure(){
        return tideMeasures.getMeasures(dateToStringForPortus());
    }

    private void persistMeasures() {
        double meanHeight = getMeanHeight();
        persistTide(meanHeight, getCanterasSurfaces(meanHeight));
        currentTime = getCurrentTime();
    }

    private void persistTide(double meanHeight, List<Integer> ints) {
        new TidesController().persistTide(getTide(meanHeight, ints, formatDate()));
    }

    private double getMeanHeight() {
        return seaHeightScrapper.getAverageHeight();
    }

    private List<Integer> getCanterasSurfaces(double meanHeight) {
        return new LasCanterasZonesModel().calculateSurfaces(meanHeight);
    }

    private Tides getTide(double meanHeight, List<Integer> surfaces, String date) {
        return new Tides(date, meanHeight, surfaces.get(ZONE_1), surfaces.get(ZONE_2), surfaces.get(ZONE_3),
                        surfaces.get(ZONE_4), surfaces.get(ZONE_5));
    }

    private LocalDateTime getCurrentTime() {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.HOURS).toLocalDateTime();
    }

    private String formatDate() {
        return currentTime.getYear() + "-" + formatNumberOfDate(currentTime.getMonthValue()) + "-" +
                formatNumberOfDate(currentTime.getDayOfMonth()) + " " + formatNumberOfDate(currentTime.getHour());
    }

    private String dateToStringForPortus() {
        int currentHour = currentTime.getHour();
        if(isDaylightSavingTime()) currentHour--;
        return formatNumberOfDate(currentTime.getDayOfMonth()) + "-" + formatNumberOfDate(currentTime.getMonthValue())
                + "-" + currentTime.getYear() + " " + formatNumberOfDate(currentHour);
    }

    private boolean isDaylightSavingTime() {
        ZonedDateTime now = getZonedDateTime();
        return now.getZone().getRules().isDaylightSavings( now.toInstant() );
    }

    private ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.of(currentTime, ZoneId.systemDefault());
    }

    private String formatNumberOfDate(int field) {
        return field < 10 ? "0" + field : field + "";
    }
}