import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class DataLoader {

    // Define the default path for easy use. Assumes file is in the project root.
    private static final String DEFAULT_PATH = "African_AQI_Cleaned.csv";

    /**
     * Reads the CSV file and constructs the hierarchical data model (Region -> Country -> City).
     * @param filepath The path to the CSV file.
     * @return A List of all top-level Region objects.
     */
    public static List<Region> load(String filepath) {
        List<Region> regions = new ArrayList<>();
        String line = ""; // Initialized for error reporting

        // Use try-with-resources for automatic resource closing
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))){

            reader.readLine(); // Skip header line

            // Core reading loop: Correctly assigns 'line' and checks for end-of-file
            while((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);

                // Check for minimum required fields (index 11 is PM2.5)
                if (fields.length < 12) {
                    System.err.println("Incomplete Line Skipped: " + line);
                    continue;
                }

                // 1. Data Extraction and Parsing (Matched to City Constructor: name, no2, pm25, ozone)
                String regionName = fields[0];
                String countryName = fields[1];
                String cityName = fields[2];

                // Pollutant values using the correct CSV indices:
                double no2 = Double.parseDouble(fields[9]);    // NO2 AQI Value (Index 9)
                double pm25 = Double.parseDouble(fields[11]);  // PM2.5 AQI Value (Index 11)
                double ozone = Double.parseDouble(fields[7]);  // Ozone AQI Value (Index 7)

                // 2. Hierarchical Lookup/Creation (Building the Region -> Country -> City structure)
                Region curRegion = findOrCreateRegion(regions, regionName);
                Country currCountry = findOrCreateCountry(curRegion, countryName);

                // 3. Create and Add City
                City newCity = new City(cityName, no2, pm25, ozone);
                currCountry.addCity(newCity);
            }

            return regions;

            // Catch blocks follow the try block immediately
        } catch (IOException exc) {
            System.err.println("File Error: Could not read file at " + filepath + ". Check path/name. Details: " + exc.getMessage());
        } catch (NumberFormatException exc) {
            System.err.println("Data Error: Failed to parse number on line: " + line + ". Details: " + exc.getMessage());
        }

        // Return the (possibly empty) list if an exception occurred
        return regions;
    }

    // ***************************************************************
    // RYAN'S CORE LOGIC: Find-or-Create Helper Methods
    // ***************************************************************

    /**
     * Finds an existing Region or creates a new one, adding it to the master list.
     */
    private static Region findOrCreateRegion(List<Region> regions, String name) {
        for (Region region : regions) {
            // Check if Region name matches
            if (region.getName().equals(name)) {
                return region;
            }
        }

        // If not found, create and add
        Region newRegion = new Region(name);
        regions.add(newRegion);
        return newRegion;
    }

    /**
     * Finds an existing Country within the Region or creates a new one, adding it to the Region's list.
     */
    private static Country findOrCreateCountry(Region region, String name) {
        // NOTE: Requires Region.getCountry(String name) from Lily's class
        Country existingCountry = region.getCountry(name);

        if (existingCountry != null) {
            return existingCountry;
        } else {
            // If not found, create new Country
            Country newCountry = new Country(name);
            // NOTE: Requires Region.addCountry(Country c) from Lily's class
            region.addCountry(newCountry);
            return newCountry;
        }
    }
}