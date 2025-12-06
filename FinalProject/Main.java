import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Loading the regions, countries, and cities from the CSV file
        List<Region> regions = DataLoader2.load("air_quality.csv");

        // Displaying a summary for each region, country, and city
        for (Region region : regions) {
            System.out.println("Region: " + region.getName());

            for (Country country : region.getCountries()) {
                System.out.println("  Country: " + country.getName());

                for (City city : country.getCities()) {
                    System.out.println("    " + city.getAnalysisReport());
                }
            }
        }

        // Finding the most polluted city
        City worstCity = null;

        for (Region region : regions) {
            for (Country country : region.getCountries()) {
                for (City city : country.getCities()) {
                    if (worstCity == null || city.getHighestAQI() > worstCity.getHighestAQI()) {
                        worstCity = city;
                    }
                }
            }
        }

        // Displaying the most polluted city
        if (worstCity != null) {
            System.out.println("\nMost Polluted City: " + worstCity.getName());
            System.out.println("Highest AQI: " + worstCity.getHighestAQI());
            System.out.println("Status: " + AQIAnalysis.getStatus(worstCity.getHighestAQI()));
        }

        // Swing chart for the visual representation
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("African Air Quality Analyzer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new ChartPanel(regions)); 
            frame.setVisible(true);
        });
    }
}
