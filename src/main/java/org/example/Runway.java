package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;


public class Runway<E extends Airplane> {
    private final String idRunaway;
    private PriorityQueue<E> airplaneList; // avioane care au status WAITING_FOR_TAKEOFF si WAITING_FOR_LANDING
    private final String runawayType;    // pista pentru decolare sau pentru aterizare
    private Comparator<E> runawayComparator;
    private final String runwayAirplaneType;
    private LocalTime runwayUnavailableTime;
    private Status1 runwayIsAvailable;

    public static String format = "HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

    private enum Status1 {
        FREE,
        OCCUPIED
    }

    public Runway(String id, String typeAp, String typeR) {
        idRunaway = id;
        // avioane wide body sau narrow body
        runawayType = typeR;
        runwayAirplaneType = typeAp;

        if (runawayType.equals("takeoff")) {
            takeoffRunway();
        } else {
            landingRunway();
        }

        airplaneList = new PriorityQueue<>(runawayComparator);
        runwayIsAvailable = Status1.FREE;
        runwayUnavailableTime = LocalTime.MIN;
    }

    public void takeoffRunway() {
        runawayComparator = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if (o1.getStatus().equals(Airplane.Status.LANDED) || o1.getStatus().equals(Airplane.Status.DEPARTED)) {
                    return 1;
                } else if (o2.getStatus().equals(Airplane.Status.LANDED) || o2.getStatus().equals(Airplane.Status.DEPARTED)) {
                    return -1;
                }
                return o1.getDesiredTime().compareTo(o2.getDesiredTime());
            }
        };
    }

    public void landingRunway() {
        runawayComparator = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if (o1.getStatus().equals(Airplane.Status.LANDED) || o1.getStatus().equals(Airplane.Status.DEPARTED)) {
                    return 1;
                }
                if (o2.getStatus().equals(Airplane.Status.LANDED) || o2.getStatus().equals(Airplane.Status.DEPARTED)) {
                    return -1;
                }
                if (o1.isUrgent() && !o2.isUrgent()) {
                    return -1;
                }
                if (!o1.isUrgent() && o2.isUrgent()) {
                    return 1;
                }
                return o1.getDesiredTime().compareTo(o2.getDesiredTime());
            }
        };
    }

    public void addAirplane(Airplane airplane, LocalTime timestamp) throws IncorrectRunwayException {
        if ((airplane.getStatus().equals(Airplane.Status.WAITING_FOR_LANDING) && runawayType.equals("takeoff")) ||
                (airplane.getStatus().equals(Airplane.Status.WAITING_FOR_TAKEOFF) && runawayType.equals("landing")) ||
                (airplane.getStatus().equals(Airplane.Status.LANDED) && runawayType.equals("takeoff")) ||
                (airplane.getStatus().equals(Airplane.Status.DEPARTED) && runawayType.equals("landing"))) {
                    throw new IncorrectRunwayException(timestamp.format(formatter) + " | The chosen runway for allocating the plane is incorrect");
        } else {
            airplaneList.add((E) airplane);
        }
    }

    public void runwayInUse(LocalTime maneuverTime) throws UnavailableRunwayException {
        if (maneuverTime.isBefore(getRunwayUnavailableTime()) || maneuverTime.equals(getRunwayUnavailableTime())) {
            String maneuverTimeString = maneuverTime.format(formatter);
            throw new UnavailableRunwayException(maneuverTimeString + " | The chosen runway for maneuver is currently occupied");
        }

        E airplane = airplaneList.poll();

        if (airplane != null) {
            if (getRunawayType().equals("takeoff")) {
                airplane.setStatus(Airplane.Status.DEPARTED);
                setRunwayUnavailableTime(maneuverTime.plusMinutes(5));
            } else {
                airplane.setStatus(Airplane.Status.LANDED);
                setRunwayUnavailableTime(maneuverTime.plusMinutes(10));
            }
            airplane.setConcretTime(maneuverTime);
            setRunwayIsAvailable(Status1.OCCUPIED);
            airplaneList.add(airplane);
        }
    }

    public boolean isRunwayAvailable(LocalTime currentTime) {
        return runwayUnavailableTime.isBefore(currentTime);
    }

    public String toString(LocalTime currentTime) {
        if (isRunwayAvailable(currentTime)) {
            setRunwayIsAvailable(Status1.FREE);
        } else {
            setRunwayIsAvailable(Status1.OCCUPIED);
        }
        StringBuilder str = new StringBuilder(getIdRunaway() + " - " + getRunwayIsAvailable());

        TreeSet<E> set = new TreeSet<>(runawayComparator);
        set.addAll(airplaneList);

        for (E airplane : set) {
            if (airplane.getStatus() == Airplane.Status.WAITING_FOR_LANDING ||
                airplane.getStatus() == Airplane.Status.WAITING_FOR_TAKEOFF) {
                str.append("\n").append(airplane);
            }
        }

        return str.toString();
    }

    public String getIdRunaway() {
        return idRunaway;
    }

    public boolean runwayContainsAirplane(String idFlight) {
        for (E airplane : getAirplaneList()) {
            if (airplane.getIdFlight().equals(idFlight)) {
                return true;
            }
        }

        return false;
    }

    public E getAirplane(String idFlight) {
        for (E airplane : airplaneList) {
            if (airplane.getIdFlight().equals(idFlight)) {
                return airplane;
            }
        }
        return null;
    }

    public String getRunawayType() {
        return runawayType;
    }

    public LocalTime getRunwayUnavailableTime() {
        return runwayUnavailableTime;
    }

    public void setRunwayIsAvailable(Status1 runwayIsAvailable) {
        this.runwayIsAvailable = runwayIsAvailable;
    }

    public void setRunwayUnavailableTime(LocalTime runwayUnavailableTime) {
        this.runwayUnavailableTime = runwayUnavailableTime;
    }

    public Status1 getRunwayIsAvailable() {
        return runwayIsAvailable;
    }

    public PriorityQueue<E> getAirplaneList() {
        return airplaneList;
    }
}
