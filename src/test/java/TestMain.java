import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.Main;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestMain {
    String antetResources = "src/main/resources/";

    public boolean areFilesEqual(Path file1, Path file2) throws IOException {
        List<String> lines1 = Files.readAllLines(file1);
        List<String> lines2 = Files.readAllLines(file2);

        if (lines1.size() != lines2.size()) {
            return false;
        }

        for (int i = 0; i < lines1.size(); i++) {
            String line1 = lines1.get(i).trim();
            String line2 = lines2.get(i).trim();

            if (!line1.equals(line2)) {
                return false;
            }
        }

        return true;
    }

    public void emptyOutput(String test_name) {
        File[] files = new File(antetResources + test_name).listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".out")) {
                file.delete();
            }
        }
    }

    @Test
    public void basicFlightSearch01() throws IOException {
        String file = "01-basic-flight-search";
        emptyOutput(file);
        Main.main(new String[]{file});
        String[] test_file_names = {
                "flight_info"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void exceptionsFlightSearch02() throws IOException {
        String file = "02-exceptions-flight-search";
        emptyOutput(file);
        Main.main(new String[]{file});
        String[] test_file_names = {
                "board_exceptions",
                "flight_info"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void basicRunwayInfo03() throws IOException {
        String file = "03-basic-runway-info";
        emptyOutput(file);
        Main.main(new String[]{file});
        String[] test_file_names = {
                "runway_info_Alpha_03:30:00",
                "runway_info_Bravo_11:00:00",
                "runway_info_Charlie_11:10:00",
                "runway_info_Delta_03:22:00",
                "runway_info_Delta_10:45:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void urgentRunwayInfo04() throws IOException {
        String file = "04-urgent-runway-info";
        emptyOutput(file);
        Main.main(new String[]{file});
        String[] test_file_names = {
                "runway_info_Alpha_03:30:00",
                "runway_info_Bravo_11:00:00",
                "runway_info_Charlie_11:10:00",
                "runway_info_Delta_03:22:00",
                "runway_info_Delta_10:45:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void maneuverPrintFree05() throws IOException {
        String file = "05-maneuver-print-free";
        emptyOutput(file);
        Main.main(new String[]{file});

        String[] test_file_names = {
                "flight_info",
                "runway_info_Alpha_11:17:00",
                "runway_info_Alpha_11:30:00",
                "runway_info_Alpha_11:43:00",
                "runway_info_Alpha_11:55:00",
                "runway_info_Delta_09:28:00",
                "runway_info_Delta_09:51:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void maneuverPrintOccupied06() throws IOException {
        String file = "06-maneuver-print-occupied";
        emptyOutput(file);
        Main.main(new String[]{file});

        String[] test_file_names = {
                "flight_info",
                "runway_info_Alpha_11:13:00",
                "runway_info_Alpha_11:20:00",
                "runway_info_Alpha_11:36:00",
                "runway_info_Alpha_11:55:00",
                "runway_info_Delta_09:20:00",
                "runway_info_Delta_09:32:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void exceptionsManeuver07() throws IOException {
        String file = "07-exceptions-maneuver";
        emptyOutput(file);
        Main.main(new String[]{file});

        String[] test_file_names = {
                "board_exceptions",
                "flight_info",
                "runway_info_Alpha_11:13:00",
                "runway_info_Alpha_11:15:00",
                "runway_info_Alpha_11:20:00",
                "runway_info_Alpha_11:36:00",
                "runway_info_Alpha_11:55:00",
                "runway_info_Delta_09:20:00",
                "runway_info_Delta_09:32:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void exceptionsAll08() throws IOException {
        String file = "08-exceptions-all";
        emptyOutput(file);
        Main.main(new String[]{file});

        String[] test_file_names = {
                "board_exceptions",
                "flight_info",
                "runway_info_Alpha_11:13:00",
                "runway_info_Alpha_11:15:00",
                "runway_info_Alpha_11:20:00",
                "runway_info_Alpha_11:36:00",
                "runway_info_Alpha_11:55:00",
                "runway_info_Delta_09:20:00",
                "runway_info_Delta_09:32:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }

    @Test
    public void allCases09() throws IOException {
        String file = "09-all-cases";
        emptyOutput(file);
        Main.main(new String[]{file});

        String[] test_file_names = {
                "board_exceptions",
                "flight_info",
                "runway_info_Alpha_01:06:00",
                "runway_info_Alpha_01:23:00",
                "runway_info_Alpha_01:39:00",
                "runway_info_Alpha_01:43:00",
                "runway_info_Bravo_04:05:00",
                "runway_info_Bravo_04:10:00",
                "runway_info_Bravo_04:48:00",
                "runway_info_Charlie_08:03:00",
                "runway_info_Charlie_08:10:00",
                "runway_info_Delta_10:03:00",
                "runway_info_Delta_10:07:00",
                "runway_info_Delta_10:11:00"
        };
        for (String test_file_name : test_file_names) {
            Path out = Paths.get(antetResources + file + "/" + test_file_name + ".out");
            Path ref = Paths.get(antetResources + file + "/" + test_file_name + ".ref");
            assertTrue(areFilesEqual(out, ref));
        }
    }
}