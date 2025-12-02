import java.util.ArrayList;

public class Country extends AbstractLocation {

    private ArrayList<City> cities;

    public Country (String name){
        super(name);
        this.cities = new ArrayList<>();
    }

    public void addCity(City c){
        this.cities.add(c);
    }

    public ArrayList <City> getCities(){
        return this.cities;
    }
    public String getMostPollutedCity(){
        if (cities.isEmpty())
            return "No cities available";

        City max = cities.get(0);

        for (City c : cities){
            if(c.getHighestAQI()> max.getHighestAQI()) {
                max=c;
            }
        }
        return max.getName();
    }



}
