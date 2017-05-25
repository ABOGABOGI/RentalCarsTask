package com.huseyin.rental;

import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.Type;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.*;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


@Path("/vehicles")
public class RetrieveVehiclesRest {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  public static void main(String[] args) throws IOException {
    //can run as a standalone console application otherwise results will appear during API usage
    printVehiclesSipp(); //q1
    System.out.println("--------------------------------------------------------------");
    printPriceSortedVehiclesAsc(); //q2
    System.out.println("--------------------------------------------------------------");
    printVehiclesSupplierRatingDesc(); //q3
    System.out.println("--------------------------------------------------------------");
    printVehiclesTotalRatingDesc(); //q4
  }

  public static String getCarType(Vehicle vehicle) throws IOException {
    String sipp = vehicle.getSipp();
    char carType = sipp.charAt(0);
    switch (carType) {
      case 'M':
        return "Mini";
      case 'E':
        return "Economy";
      case 'C':
        return "Compact";
      case 'I':
        return "Intermediate";
      case 'S':
        return "Standard";
      case 'F':
        return "Full size";
      case 'P':
        return "Premium";
      case 'L':
        return "Luxury";
      case 'X':
        return "Special";
    }
    return "Unspecified Car Type";
  }

  public static String getDoorsAndCarType(Vehicle vehicle) throws IOException {
    String sipp = vehicle.getSipp();
    char doorsAndCarType = sipp.charAt(1);
    switch (doorsAndCarType) {
      case 'B':
        return "2 doors";
      case 'C':
        return "4 doors";
      case 'D':
        return "5 doors";
      case 'W':
        return "Estate";
      case 'T':
        return "Convertible";
      case 'F':
        return "SUV";
      case 'P':
        return "Pick up";
      case 'V':
        return "Passenger Van";
    }
    return "Unspecified Car Type/Doors";

  }

  public static String getTransmission(Vehicle vehicle) throws IOException {
    String sipp = vehicle.getSipp();
    char transmission = sipp.charAt(2);
    switch (transmission) {
      case 'M':
        return "Manual";
      case 'A':
        return "Automatic";
    }
    return "Unspecified Transmission";
  }

  public static String getFuelAndAirCon(Vehicle vehicle) throws IOException {
    String sipp = vehicle.getSipp();
    char fuelAndAirCon = sipp.charAt(3);
    switch (fuelAndAirCon) {
      case 'N':
        return "Petrol/no AC";
      case 'R':
        return "Petrol/AC";
    }
    return "Unspecified Fuel/AC";
  }

  public static Double getVehicleScore(Vehicle vehicle) throws IOException {
    Double vehicleScore = 0.0;
    switch (getTransmission(vehicle)) {
      case "Manual":
        vehicleScore = 1.0;
        break;
      case "Automatic":
        vehicleScore = 5.0;
        break;
    }
    if (getFuelAndAirCon(vehicle) == "Petrol/AC") {
      vehicleScore += 2.0;
    }
    vehicle.setVehicleScore(vehicleScore);
    return vehicle.getVehicleScore();
  }

  public static Double getTotalRatingScore(Vehicle vehicle) throws IOException {
    Double totalRatingScore = 0.0;
    totalRatingScore = vehicle.getVehicleScore() + vehicle.getRating();
    vehicle.setSumOfScoreAndRating(totalRatingScore);
    return vehicle.getSumOfScoreAndRating();
  }

  public static List<Vehicle> parseVehicleFromJson() throws IOException {
    String sURL = "http://www.rentalcars.com/js/vehicles.json";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.connect();

    JsonParser jp = new JsonParser(); // from gson
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
    JsonObject rootObj = root.getAsJsonObject().getAsJsonObject("Search"); 
    JsonArray vehicleArr = rootObj.getAsJsonArray("VehicleList");
    Gson gson = new Gson();
    Type collectionType = new TypeToken<List<Vehicle>>() {}.getType();
    List<Vehicle> vehicles = gson.fromJson(vehicleArr, collectionType);
    return vehicles;
  }

  public static List<Vehicle> printPriceSortedVehiclesAsc() throws IOException {
    List<Vehicle> vehicles = parseVehicleFromJson();
    List<Vehicle> vehiclesPriceSorted = new ArrayList<Vehicle>();
    vehicles.sort((Vehicle v1, Vehicle v2) -> v1.getPrice().compareTo(v2.getPrice()));
    int i = 1;
    for (Vehicle vehicle : vehicles) {
      Vehicle vehiclePriceSorted = new Vehicle();
      vehiclePriceSorted.setName(vehicle.getName());
      vehiclePriceSorted.setPrice(vehicle.getPrice());
      vehiclesPriceSorted.add(vehiclePriceSorted);
      System.out.print(i + ". " + vehicle.getName() + " - " + vehicle.getPrice() + "\n");
      i++;
    }
    return vehiclesPriceSorted;
  }

  public static List<Vehicle> printVehiclesSipp() throws IOException {
    List<Vehicle> vehicles = parseVehicleFromJson();
    List<Vehicle> vehiclesSipp = new ArrayList<Vehicle>();
    int i = 1;
    for (Vehicle vehicle : vehicles) {
      Vehicle vehicleSipp = new Vehicle();
      vehicleSipp.setName(vehicle.getName());
      vehicleSipp.setSipp(vehicle.getSipp());
      vehicleSipp.setCarType(getCarType(vehicle));
      vehicleSipp.setCarTypeAndDoors(getDoorsAndCarType(vehicle));
      vehicleSipp.setTransmission(getTransmission(vehicle));
      vehicleSipp.setFuelAndAirCon(getFuelAndAirCon(vehicle));
      vehiclesSipp.add(vehicleSipp);

      System.out.print(i + ". " + vehicle.getName() + " - " + vehicle.getSipp() + " - "
          + getCarType(vehicle) + " - " + getDoorsAndCarType(vehicle) + " - "
          + getTransmission(vehicle) + " - " + getFuelAndAirCon(vehicle) + "\n");
      i++;
    }
    return vehiclesSipp;
  }

  public static List<Vehicle> printVehiclesSupplierRatingDesc() throws IOException {
    List<Vehicle> vehicles = parseVehicleFromJson();
    List<Vehicle> vehiclesRating = new ArrayList<Vehicle>();
    vehicles.sort((Vehicle v1, Vehicle v2) -> v2.getRating().compareTo(v1.getRating()));
    int i = 1;
    for (Vehicle vehicle : vehicles) {
      Vehicle vehicleRating = new Vehicle();
      vehicleRating.setName(vehicle.getName());
      vehicleRating.setCarType(getCarType(vehicle));
      vehicleRating.setSupplier(vehicle.getSupplier());
      vehicleRating.setRating(vehicle.getRating());
      vehiclesRating.add(vehicleRating);
      System.out.print(i + ". " + vehicle.getName() + " - " + getCarType(vehicle) + " - "
          + vehicle.getSupplier() + " - " + vehicle.getRating() + "\n");
      i++;
    }
    return vehiclesRating;

  }

  public static List<Vehicle> printVehiclesTotalRatingDesc() throws IOException {
    List<Vehicle> vehicles = parseVehicleFromJson();
    List<Vehicle> vehiclesTotalRating = new ArrayList<Vehicle>();
    int i = 1;
    vehicles.forEach((vehicle) -> {
      try {
        getVehicleScore(vehicle);
        getTotalRatingScore(vehicle);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    vehicles.sort((Vehicle v1, Vehicle v2) -> v2.getSumOfScoreAndRating()
        .compareTo(v1.getSumOfScoreAndRating()));
    for (Vehicle sortedVehicle : vehicles) {
      Vehicle vehicleTotalRating = new Vehicle();
      vehicleTotalRating.setName(sortedVehicle.getName());
      vehicleTotalRating.setVehicleScore(sortedVehicle.getVehicleScore());
      vehicleTotalRating.setRating(sortedVehicle.getRating());
      vehicleTotalRating.setSumOfScoreAndRating(sortedVehicle.getSumOfScoreAndRating());
      vehiclesTotalRating.add(vehicleTotalRating);
      System.out.print(
          i + ". " + sortedVehicle.getName() + " - " + sortedVehicle.getVehicleScore() + " - "
              + sortedVehicle.getRating() + " - " + sortedVehicle.getSumOfScoreAndRating() + "\n");
      i++;
    }
    return vehiclesTotalRating;
  }

  public String marshallingJson(List<Vehicle> vehicleList) throws JAXBException {
    Vehicles vehicles = new Vehicles();
    JAXBContext jaxbContext;
    jaxbContext = JAXBContext.newInstance(Vehicle.class, Vehicles.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
    jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    for (Vehicle vehicle : vehicleList) {
      vehicles.addVehicle(vehicle);
    }
    StringWriter writer = new StringWriter();
    jaxbMarshaller.marshal(vehicles, writer);
    return writer.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/vehicleOriginal")
  public String getVehiclesJson() throws IOException, JAXBException {
    return marshallingJson(parseVehicleFromJson());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/vehiclePrices")
  public String getVehiclePricesSortedJson() throws IOException, JAXBException {
    return marshallingJson(printPriceSortedVehiclesAsc());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/vehicleSipp")
  public String getVehicleSippJson() throws IOException, JAXBException {
    return marshallingJson(printVehiclesSipp());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/vehicleRatings")
  public String getVehicleSupplierRatingJson() throws IOException, JAXBException {
    return marshallingJson(printVehiclesSupplierRatingDesc());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/vehicleTotalRatings")
  public String getVehicleTotalRatingJson() throws IOException, JAXBException {
    return marshallingJson(printVehiclesTotalRatingDesc());
  }

}

