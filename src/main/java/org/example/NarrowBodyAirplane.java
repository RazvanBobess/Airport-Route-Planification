package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class NarrowBodyAirplane extends Airplane {

    public NarrowBodyAirplane(String model1, String id, String leaving, String destination1,
                              LocalTime wishedTime) {
        super(model1, id, leaving, destination1, wishedTime);
    }

    public String toString() {
        return "Narrow Body - " + super.toString();
    }

}
