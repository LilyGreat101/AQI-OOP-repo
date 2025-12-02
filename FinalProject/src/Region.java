import java.util.ArrayList;


public class Region extends AbstractLocation {

    private ArrayList<Country> countries;

    public Region(String name){
        super(name);
        this.countries = new ArrayList<>();
    }

    public void addCountry(Country country){
        this.countries.add(country);
    }

    public ArrayList<Country> getCountries(){
        return countries;
    }






}
