import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Loading the regions, countries, and cities from the CSV file
        List<Region> regions = DataLoader2.load("African_AQI_CSV.csv");



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

        // 1. Initialize the Analytic Brain
        StatsCalculator stats = new StatsCalculator();

// 2. Retrieve the sorted Top 10 worst cities
        List<City> top10 = stats.getTop10WorstCities(regions);

// 3. Print the formatted Leaderboard
        System.out.println("\n--- TOP 10 POLLUTED CITIES LEADERBOARD ---");
        for (int i = 0; i < top10.size(); i++) {
            City city = top10.get(i);
            double aqiValue = city.getAqi(); // Uses your new standardize getter
            String status = AQIAnalysis.getStatus(aqiValue); // Uses the fixed status logic

            System.out.printf("%d. %s: %.2f (%s)\n", (i + 1), city.getName(), aqiValue, status);
        }

        // Finding the most polluted city
        City worstCity = null;

        for (Region region : regions) {
            for (Country country : region.getCountries()) {
                for (City city : country.getCities()) {
                    if (worstCity == null || city.getAqi() > worstCity.getAqi()) {
                        worstCity = city;
                    }
                }
            }
        }



        // Displaying the most polluted city
        if (worstCity != null) {
            System.out.println("\nMost Polluted City: " + worstCity.getName());
            System.out.println("Highest AQI: " + worstCity.getAqi());
            System.out.println("Status: " + AQIAnalysis.getStatus(worstCity.getAqi()));
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
