package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * Class used by sub_main.xhtml that allows the user to pick from an array of types 
 * from a drop down checkbox
 * 
 *@author ekoranda1
 */
@ManagedBean
public class TypeView {
	
	
 

    private String[] selectedTypes;
    private List<String> types;

    /**
     * Adds the different types to the array List
     */
    @PostConstruct
    public void init() {
        types = new ArrayList<String>();
        types.add("Data Presentation");
        types.add("Next Gen Sequencing");
        types.add("Sequence Analysis");
        types.add("Structural Biology");
        
 

    }
 
    public String[] getSelectedTypes() {
        return selectedTypes;
    }
 
    public void setSelectedTypes(String[] selectedTypes) {
        this.selectedTypes = selectedTypes;
    }

 
    public List<String> getTypes() {
        return types;
    }
 


 
}