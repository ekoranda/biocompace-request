package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ItemsBean {

    private List<String> cities;
    private String[] selectedCities;

 
    @PostConstruct
    public void init() {
        cities = new ArrayList<String>();
        cities.add("Miami");
        cities.add("London");
        cities.add("Paris");
        cities.add("Istanbul");
        cities.add("Berlin");
        cities.add("Barcelona");
        cities.add("Rome");
        cities.add("Brasilia");
        cities.add("others");
         
        
    }
 
    
    public List<String> getCities() {
        return cities;
    }
    public String[] getSelectedCities() {
        System.out.println(Arrays.toString(selectedCities));
        return selectedCities;
    }
    public void setSelectedCities(String[] selectedCities) {
        this.selectedCities = selectedCities;
    }
 

}