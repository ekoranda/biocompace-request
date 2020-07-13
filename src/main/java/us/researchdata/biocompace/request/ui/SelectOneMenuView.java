package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SelectOneMenuView {
     
    private String rtl;
    private ArrayList<String> items = new ArrayList();

    
     
   
     
    @PostConstruct
    public void init() {
    	items.addAll(Arrays.asList
                ("red", "blue", "others"));
    }
 
    
 
    public String getRtl() {
        return rtl;
    }
 
    public void setRtl(String rtl) {
        this.rtl = rtl;
    }
    
    public ArrayList<String> getItems(){
    	return items;
    }
    
    public void setItems(String item) {
    	this.items.add(item);
    }
 
   
}