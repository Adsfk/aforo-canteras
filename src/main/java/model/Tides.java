package model;

public class Tides {

    private final String date;
    private final double tide;
    private final double zone1;
    private final double zone2;
    private final double zone3;
    private final double zone4;
    private final double zone5;

    public Tides(String date, double tide, double zone1, double zone2, double zone3,
                 double zone4, double zone5){
        this.date = date;
        this.tide = tide;
        this.zone1 = zone1;
        this.zone2 = zone2;
        this.zone3 = zone3;
        this.zone4 = zone4;
        this.zone5 = zone5;
    }

    public String getDate() {
        return date;
    }

    public double getTide() {
        return tide;
    }

    public double getZone1() {
        return zone1;
    }

    public double getZone2() {
        return zone2;
    }

    public double getZone3() {
        return zone3;
    }

    public double getZone4() {
        return zone4;
    }

    public double getZone5() {
        return zone5;
    }
}
