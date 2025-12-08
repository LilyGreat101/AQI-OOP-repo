import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ChartPanel extends JPanel {

    private List<Region> regions;

    // Constructor to hold list of regions
    public ChartPanel(List<Region> regions) {
        this.regions = regions;
        setBackground(Color.WHITE); // Background of graph white
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // 1. Initialize calculator to get the Top 10 cities
        StatsCalculator stats = new StatsCalculator();
        List<City> top10 = stats.getTop10WorstCities(regions);

        if (top10.isEmpty()) return;

        // 2. Find max AQI from top of sorted list for scaling
        double maxAQI = top10.get(0).getAqi();

        int padding = 60;
        // 3. Spacing for 10 cities ensures a positive width for g2.fillRect
        int barWidth = (panelWidth - 2 * padding) / top10.size();
        int x = padding;

        for (City city : top10) {
            double aqi = city.getAqi(); // Standardized getter added to City class

            // Bar height math based on frame size
            int barHeight = (int) ((aqi / maxAQI) * (panelHeight - 150));

            // Get color from logic using standardize aqiValue
            String status = AQIAnalysis.getStatus(aqi);
            g2.setColor(getColorByStatus(status));

            // Draw the bar with a positive width offset (barWidth - 15)
            g2.fillRect(x, panelHeight - barHeight - 70, barWidth - 15, barHeight);

            // Draw Labels (Name and numeric value)
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(city.getName(), x, panelHeight - 50);
            g2.drawString(String.valueOf((int) aqi), x, panelHeight - barHeight - 75);

            x += barWidth;
        }
    }

    // Helper method to pick bar color based on status
    private Color getColorByStatus(String status) {
        switch (status) {
            case "Good":
                return Color.GREEN;
            case "Moderate":
                return Color.YELLOW;
            case "Unhealthy for Sensitive People":
                return Color.ORANGE;
            case "Unhealthy":
                return Color.RED;
            case "Very Unhealthy":
                return new Color(128, 0, 128); // Purple
            case "Hazardous":
                return new Color(139, 69, 19); // Brown
            default:
                return Color.GRAY;
        }
    }
}