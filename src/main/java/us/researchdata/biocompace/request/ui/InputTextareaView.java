package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;



@ManagedBean
public class InputTextareaView {
     
    public List<String> completeArea(String query) {
        List<String> results = new ArrayList<String>();
         
 
            for(int i = 0; i < 250; i++) {
                results.add(query + i);
            }
        
         
        return results;
    }
}

