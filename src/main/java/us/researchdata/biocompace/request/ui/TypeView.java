package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean
public class TypeView {
 
    private String[] selectedConsoles;
    private String[] selectedConsoles2;

    private String[] selectedTypes;
    private List<String> types;

 
    @PostConstruct
    public void init() {
        types = new ArrayList<String>();
        types.add("Data Presentation");
        types.add("Next Gen Sequencing");
        types.add("Sequence Analysis");
        types.add("Structural Biology");
        
 

    }
 
    public String[] getSelectedConsoles() {
        return selectedConsoles;
    }
 
    public void setSelectedConsoles(String[] selectedConsoles) {
        this.selectedConsoles = selectedConsoles;
    }
 

 

 
    public String[] getSelectedTypes() {
        return selectedTypes;
    }
 
    public void setSelectedTypes(String[] selectedTypes) {
        this.selectedTypes = selectedTypes;
    }
 
    public String[] getSelectedConsoles2() {
        return selectedConsoles2;
    }
 
    public void setSelectedConsoles2(String[] selectedConsoles2) {
        this.selectedConsoles2 = selectedConsoles2;
    }
 
    public List<String> getTypes() {
        return types;
    }
 


 
}