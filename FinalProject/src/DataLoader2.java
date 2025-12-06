import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataLoader2 {

    public static List<Region> load(String file) {
        List<Region> regions = new ArrayList<>();
        BufferedReader reader = null;
        String line = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",", -1);

                if (columns.length < 14) {
                    System.out.println("There is null data here so we skipped, check it" + line);
                    continue;
                }

                String name_city = columns[2].trim();
                String name_country = columns[1].trim();
                String name_region = columns[0].trim();

                double aqi = Double.parseDouble(columns[3].trim());
                double pm25 = Double.parseDouble(columns[11].trim());
                double ozone_aqi = Double.parseDouble(columns[7].trim());

                Region currregion = findorcreateregion(regions, name_region);
                Country currcountry = currregion.getCountry(name_country);

                if (currcountry == null) {
                    currcountry = new Country(name_country);
                    currregion.addCountry(currcountry);
                }

                City ncity = new City(name_city, aqi, pm25, ozone_aqi);
                currcountry.addCity(ncity);
            }
        } catch (IOException e) {
            System.out.println("Sorry, file cant be read." + file);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Sorry, there is an issue with the parsing" + line);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Cant close the reader");
                }
            }
        }

        return regions;
    }

    private static Region findorcreateregion(List<Region> regions, String name) {
        for (Region region : regions) {
            if (region.getName().equalsIgnoreCase(name)) {
                return region;
            }
        }
        Region newRegion = new Region(name);
        regions.add(newRegion);
        return newRegion;
    }
}
