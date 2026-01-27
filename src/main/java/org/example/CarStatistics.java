package org.example;

public class CarStatistics {
    private final int lowestPrice;
    private final String lowestPriceLink;
    private final int highestPrice;
    private final String highestPriceLink;
    private final int averagePrice;

    public CarStatistics(int lowestPrice, String lowestPriceLink,
                         int highestPrice, String highestPriceLink,
                         int averagePrice) {
        this.lowestPrice = lowestPrice;
        this.lowestPriceLink = lowestPriceLink;
        this.highestPrice = highestPrice;
        this.highestPriceLink = highestPriceLink;
        this.averagePrice = averagePrice;
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    public String getLowestPriceLink() {
        return lowestPriceLink;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public String getHighestPriceLink() {
        return highestPriceLink;
    }

    public int getAveragePrice() {
        return averagePrice;
    }
}
