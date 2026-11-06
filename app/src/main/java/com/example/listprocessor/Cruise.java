package com.example.listprocessor;

public class Cruise {
    private String cruiseLine = "Placeholder";
    private String shipName = "None";
    private String numCode = "0000";
    private String cruiseRegion = "N/A";
    private int cruiseLength = 0;
    private double price = 0;
    private double gratuity = 0;



    public void setCruiseLine(String cruiseLine) {
        this.cruiseLine = cruiseLine;
    }
    public String getCruiseLine() {
        return cruiseLine;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public String getShipName() {
        return shipName;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }
    public String getNumCode() {
        return numCode;
    }

    public void setCruiseRegion(String cruiseRegion) {
        this.cruiseRegion = cruiseRegion;
    }
    public String getCruiseRegion() {
        return cruiseRegion;
    }

    public void setCruiseLength(int cruiseLength) {
        this.cruiseLength = cruiseLength;
    }
    public int getCruiseLength() {
        return cruiseLength;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }

    public void setGratuity(double gratuity){this.gratuity = gratuity;}
    public double getGratuity(){
        return gratuity;
    }

    public double getTotalGratuity(double gratuity, int cruiseLength){
        return gratuity*cruiseLength;
    }

    public double getTotalCost(int price, double gratuity, int cruiseLength){
        double totalGratuity = getTotalGratuity(gratuity, cruiseLength);
        return price+totalGratuity;
    }
}
