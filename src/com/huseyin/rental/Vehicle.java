package com.huseyin.rental;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"name", "vehicleScore", "price", "sipp", "carType", "carTypeAndDoors", "transmission", "fuelAndAirCon", "supplier", "rating", "sumOfScoreAndRating" })
public class Vehicle {
  private String sipp;
  private String name;
  private Double price;
  private String supplier;
  private Double rating;
  private Double vehicleScore;
  private Double sumOfScoreAndRating;
  private String carType;
  private String carTypeAndDoors;
  private String transmission;
  private String fuelAndAirCon;

  public Vehicle(String sipp) {
    this.sipp = sipp;
  }
  public Vehicle() {}
  
  public Vehicle(String sipp, Double price, String supplier, Double rating) {
    super();
    this.sipp = sipp;
    this.price = price;
    this.supplier = supplier;
    this.rating = rating;
  }
  
  
  public String getSipp() {
    return sipp;
  }
  public void setSipp(String sipp) {
    this.sipp = sipp;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public String getSupplier() {
    return supplier;
  }
  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }
  public Double getRating() {
    return rating;
  }
  public void setRating(Double rating) {
    this.rating = rating;
  }
  public Double getVehicleScore() {
    return vehicleScore;
  }
  public void setVehicleScore(Double vehicleScore) {
    this.vehicleScore = vehicleScore;
  }
  public Double getSumOfScoreAndRating() {
    return sumOfScoreAndRating;
  }
  public void setSumOfScoreAndRating(Double sumOfScoreAndRating) {
    this.sumOfScoreAndRating = sumOfScoreAndRating;
  }
  public String getCarType() {
    return carType;
  }
  public void setCarType(String carType) {
    this.carType = carType;
  }
  public String getCarTypeAndDoors() {
    return carTypeAndDoors;
  }
  public void setCarTypeAndDoors(String carTypeAndDoors) {
    this.carTypeAndDoors = carTypeAndDoors;
  }
  public String getTransmission() {
    return transmission;
  }
  public void setTransmission(String transmission) {
    this.transmission = transmission;
  }
  public String getFuelAndAirCon() {
    return fuelAndAirCon;
  }
  public void setFuelAndAirCon(String fuelAndAirCon) {
    this.fuelAndAirCon = fuelAndAirCon;
  }
  @Override
  public String toString() {
    return "Vehicle [sipp=" + sipp + ", name=" + name + ", price=" + price + ", supplier="
        + supplier + ", rating=" + rating + ", vehicleScore=" + vehicleScore
        + ", sumOfScoreAndRating=" + sumOfScoreAndRating + ", carType=" + carType
        + ", carTypeAndDoors=" + carTypeAndDoors + ", transmission=" + transmission
        + ", fuelAndAirCon=" + fuelAndAirCon + "]";
  }
}
