package model;

public class Restriction {
    private String name;
    private String category;
    private String limitantTo;
    private boolean isActive;
    private Double capacityValue;
    private String capacityType;
    private CapacityCalculator capacityCalculator;

    public Restriction() {
    }

    public Restriction(String name, String limitantTo, String category, boolean isActive, Double capacityValue,
                       String capacityType, CapacityCalculator capacityCalculator) {
        this.name = name;
        this.category = category;
        this.limitantTo = limitantTo;
        this.isActive = isActive;
        this.capacityValue = capacityValue;
        this.capacityType = capacityType;
        this.capacityCalculator = capacityCalculator;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getLimitantTo() {
        return limitantTo;
    }

    public Double getCapacityValue() {
        return capacityValue;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public CapacityCalculator getCapacityCalculator() {
        return capacityCalculator;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLimitantTo(String limitantTo) {
        this.limitantTo = limitantTo;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCapacityValue(Double capacityValue) {
        this.capacityValue = capacityValue;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public void setCapacityCalculator(CapacityCalculator capacityCalculator) {
        this.capacityCalculator = capacityCalculator;
    }

    public String serialize() {
        return name + ";" + limitantTo + ";" + category + ";" + isActive + ";" + capacityType + ":" + capacityValue;
    }
}
