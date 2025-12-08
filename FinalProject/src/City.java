public class City extends AbstractLocation {
    private double no2_aqi;
    private double pm25_aqi;
    private double ozone_aqi;



    public City(String name, double no2_aqi, double pm25_aqi, double ozone_aqi) {
        super(name);
        this.no2_aqi = no2_aqi;
        this.pm25_aqi = pm25_aqi;
        this.ozone_aqi = ozone_aqi;
    }

    public City(String name, int highestAQI) {
        super(name);
        this.no2_aqi = highestAQI;
        this.pm25_aqi = highestAQI;
        this.ozone_aqi = highestAQI;
    }

    public double getHighestAQI() {
        return Math.max(pm25_aqi, Math.max(no2_aqi, ozone_aqi));
    }

    public double getPm25() {
        return pm25_aqi;
    }

    public double getAqi() {
        return getHighestAQI();
    }


    public String getAnalysisReport() {
        double highest = getHighestAQI();
        String status = AQIAnalysis.getStatus(highest);
        boolean harmful = AQIAnalysis.isHarmful(highest);

        return "City: " + getName() + "\nHighest AQI: " + highest + "\nStatus: " + status + "\nHarmful: " + harmful;
    }
}
