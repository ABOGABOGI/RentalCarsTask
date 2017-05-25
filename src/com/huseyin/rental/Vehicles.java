package com.huseyin.rental;
import java.util.ArrayList;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Vehicles")
public class Vehicles {
  
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    @XmlElements(value = {@XmlElement(name = "Vehicle", type = Vehicle.class)})
    public List<Vehicle> getVehicles() {
      return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
      vehicles.add(vehicle);
    }
 
}
