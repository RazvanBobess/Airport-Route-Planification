package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Airplane {
    private String model;
    private String idFlight;
    private String leavingLocation;
    private String destination;
    private int urgent;
    private static String format = "HH:mm:ss";

     private LocalTime desiredTime;          // timp dorit de decolare/aterizare
     private LocalTime concretTime;         // timp concret de decolare/aterizare

    public enum Status {
        WAITING_FOR_TAKEOFF,
        DEPARTED,
        WAITING_FOR_LANDING,
        LANDED
    }

    private Status status;

    public Airplane(String model1, String id, String leaving, String destination1,
                     LocalTime wishedTime) {
         model = model1;
         idFlight = id;
         leavingLocation = leaving;
         destination = destination1;
         desiredTime = wishedTime;
         urgent = 0;

         if (leaving.equals("Bucharest"))
             status = Status.WAITING_FOR_TAKEOFF;
         else
             status = Status.WAITING_FOR_LANDING;
     }

    public String toString() {
        String desiredTimeFormated = getDesiredTime().format(DateTimeFormatter.ofPattern(format));

        if (getConcretTime() != null) {
            String concretTimeFormated = getConcretTime().format(DateTimeFormatter.ofPattern(format));

            return getModel() + " - " + getIdFlight() + " - " + getLeavingLocation() +
                    " - " + getDestination() + " - " + getStatus() + " - " + desiredTimeFormated +
                    " - " + concretTimeFormated;
        }

        return getModel() + " - " + getIdFlight() + " - " + getLeavingLocation() +
                " - " + getDestination() + " - " + getStatus() + " - " + desiredTimeFormated;
    }

    public String getModel() {
        return model;
    }

    public String getIdFlight() {
        return idFlight;
    }

    public String getLeavingLocation() {
        return leavingLocation;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public LocalTime getConcretTime() {
        return concretTime;
    }

    public void setConcretTime(LocalTime concretTime) {
        this.concretTime = concretTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isUrgent() {
        return urgent == 1;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }
}
