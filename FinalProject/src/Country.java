import java.util.ArrayList;

public class Country extends AbstractLocation {

    private ArrayList<City> cities;

    public Country (String name){
        super(name);
        this.cities = new ArrayList<>();
    }

    public void addCity(City country){
        this.cities.add(country);
    }

    public ArrayList <City> getCities(){
        return this.cities;
    }
    public String getMostPollutedCity(){
        if (cities.isEmpty())
            return "No cities available";

        City max = cities.get(0);

        for (City country : cities){
            if(country.getHighestAQI()> max.getHighestAQI()) {
                max=country;
            }
        }
        return max.getName();
    }



}

