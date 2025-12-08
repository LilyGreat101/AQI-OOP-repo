import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class StatsCalculator {

        // Returns the single worst city by AQI
        public City findMostPollutedCity(List<Region> regions) {
            City worst = null;
            for (Region r : regions) {
                for (Country c : r.getCountries()) {
                    for (City city : c.getCities()) {
                        if (worst == null || city.getAqi() > worst.getAqi()) {
                            worst = city;
                        }
                    }
                }
            }
            return worst;
        }

        // Returns the single worst city by PM2.5 (The "Fine Particle" Killer)
        public City findWorstPM25City(List<Region> regions) {
            City worst = null;
            for (Region r : regions) {
                for (Country c : r.getCountries()) {
                    for (City city : c.getCities()) {
                        // Assuming City has a getPm25() method
                        if (worst == null || city.getPm25() > worst.getPm25()) {
                            worst = city;
                        }
                    }
                }
            }
            return worst;
        }


        public List<City> getTop10WorstCities(List<Region> regions) {
            List<City> allCities = new ArrayList<>();

            // 1. Flatten the data: Get every city into one big list
            for (Region r : regions) {
                for (Country c : r.getCountries()) {
                    allCities.addAll(c.getCities());
                }
            }


            allCities.sort((c1, c2) -> Double.compare(c2.getAqi(), c1.getAqi()));

            // 3. Return only the first 10 (or less if we don't have 10)
            int limit = Math.min(allCities.size(), 10);
            return allCities.subList(0, limit);
        }


        public Map<String, Double> getAverageAQIPerCountry(List<Region> regions) {
            Map<String, Double> countryAverages = new HashMap<>();

            for (Region r : regions) {
                for (Country c : r.getCountries()) {
                    double total = 0;
                    double count = 0;

                    if (c.getCities() != null) {
                        for (City city : c.getCities()) {
                            total += city.getAqi();
                            count++;
                        }
                    }

                    double avg = (count > 0) ? (total / count) : 0.0;
                    countryAverages.put(c.getName(), avg);
                }
            }
            return countryAverages;
        }
    }
