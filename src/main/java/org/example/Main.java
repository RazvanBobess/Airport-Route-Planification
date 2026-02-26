package org.example;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;

public class Main {
    public static String path = "src/main/resources/";
    public static String inputFile = "/input.in";
    public static String exceptionsFile = "/board_exceptions.out";
    public static String flightInfo = "/flight_info.out";
    public static String format = "HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

    public static Map<String, Runway<? extends Airplane>> airport = new HashMap<>();

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(args[0]);
        } else {
            System.out.println("Tema2");
        }

        String fileName = args[0];
        String filePath = path + fileName;

        String inputFilePath = Paths.get(path, fileName, inputFile).toString();
        String exceptionsFilePath = Paths.get(path, fileName, exceptionsFile).toString();
        String flightFilePath = Paths.get(path, fileName, flightInfo).toString();


        try (BufferedReader fileInputReader = new BufferedReader(new FileReader(inputFilePath))){
            String line;

            while ((line = fileInputReader.readLine()) != null) {
                handleCommands(line, exceptionsFilePath, flightFilePath, filePath);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        }
    }

    private static void handleCommands(String line, String exceptionFileWriter, String flightFileWriter, String filePath) {
        try {
            String [] parts = line.split(" - ");
            LocalTime dataTime = LocalTime.parse(parts[0].trim());
            String command = parts[1].trim();

                switch (command) {
                    case "add_runway_in_use" :
                        handleAddrunway(parts);
                        break;

                    case "allocate_plane" :
                        handleAllocateAirplane(parts, dataTime, exceptionFileWriter);
                        break;

                    case "permission_for_maneuver" :
                        handlePermissionForManeuver(parts, dataTime, exceptionFileWriter);
                        break;

                    case "runway_info" :
                        handleRunwayInfo(parts, dataTime, filePath);
                        break;

                    case "flight_info" :
                        handleFlightInfo(parts, dataTime, flightFileWriter);
                        break;

                    case "exit" :
                        return;
                }
            } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleAddrunway(String[] parts) {
        String runwayID = parts[2].trim();
        String runwayType = parts[3].trim();
        String runwayAirplaneType = parts[4].trim();

        if (runwayType.equals("narrow body")) {
            airport.put(runwayID, new Runway<NarrowBodyAirplane>(runwayID, runwayAirplaneType, runwayType));
        } else {
            airport.put(runwayID, new Runway<WideBodyAirplane>(runwayID, runwayAirplaneType, runwayType));
        }
    }

    private static void handleAllocateAirplane(String[] parts, LocalTime dataTime, String exceptionsFilePath) {
            String airplaneType = parts[2].trim();
            String airplaneModel = parts[3].trim();
            String idFlightInfo = parts[4].trim();
            String leavingLocation = parts[5].trim();
            String destination = parts[6].trim();
            LocalTime desiredTime = LocalTime.parse(parts[7].trim());
            String runwayID = parts[8].trim();

            Airplane airplane;

            if (airplaneType.equals("narrow body")) {
                airplane = new NarrowBodyAirplane(airplaneModel, idFlightInfo, leavingLocation, destination, desiredTime);
            } else {
                airplane = new WideBodyAirplane(airplaneModel, idFlightInfo, leavingLocation, destination, desiredTime);
            }

            if (parts.length == 10) {
                airplane.setUrgent(1);
            }

        Runway<?> runway = airport.get(runwayID);
            try {
                runway.addAirplane(airplane, dataTime);
            } catch (IncorrectRunwayException e) {
                try (BufferedWriter exceptionsFileWriter = new BufferedWriter(new FileWriter(exceptionsFilePath, true))) {
                    exceptionsFileWriter.write(e.getMessage());
                    exceptionsFileWriter.newLine();
                    exceptionsFileWriter.flush();

                } catch (IOException ioException) {
                    System.out.println("Error writing exception file");
                }
            }
    }

    private static void handlePermissionForManeuver(String[] parts, LocalTime dataTime, String exceptionsFilePath) {
        String runwayID = parts[2].trim();
        Runway<?> runway =  airport.get(runwayID);

        try {
            runway.runwayInUse(dataTime);
        } catch (UnavailableRunwayException e) {
            try (BufferedWriter exceptionsFileWriter = new BufferedWriter(new FileWriter(exceptionsFilePath, true))) {
                exceptionsFileWriter.write(e.getMessage());
                exceptionsFileWriter.newLine();
                exceptionsFileWriter.flush();

            } catch (IOException ioException) {
                System.out.println("Error writing exception file");
            }
        }
    }

    private static void handleRunwayInfo(String[] parts, LocalTime dataTime, String flightRunwayInfo) {
        String runwayID = parts[2].trim();
        Runway<? extends Airplane> runway = airport.get(runwayID);

        if (runway == null) {
            System.out.println("No runway found: " + runwayID);
            return;
        }

        String filename = "/runway_info_" + runwayID + "_" + dataTime.format(formatter) + ".out";
        String filePath = flightRunwayInfo + filename;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(runway.toString(dataTime));
            writer.newLine();
            writer.flush();
        } catch (IOException ioException) {
            System.out.println("Error writing runway info file: " + filePath);
        }
    }

    private static void handleFlightInfo(String[] parts, LocalTime dataTime, String flightFilePath) {
        String IDFlight = parts[2].trim();
        String formattedTime = dataTime.format(formatter);

        for (Runway<? extends Airplane> runway : airport.values()) {
            if (runway.runwayContainsAirplane(IDFlight)) {
                Airplane airplane = runway.getAirplane(IDFlight);

                try (BufferedWriter flightFileWriter = new BufferedWriter(new FileWriter(flightFilePath, true))) {
                    flightFileWriter.write(formattedTime + " | " + airplane.toString());
                    flightFileWriter.newLine();
                    flightFileWriter.flush();
                } catch (IOException ioException) {
                    System.out.println("Error: writing flight info file did not open");
                }
                break;
            }
        }
    }
}