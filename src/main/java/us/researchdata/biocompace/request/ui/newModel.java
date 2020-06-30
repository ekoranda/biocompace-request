package us.researchdata.biocompace.request.ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;

import org.imixs.workflow.bpmn.BPMNModel;
import org.imixs.workflow.bpmn.BPMNParser;
import org.imixs.workflow.engine.ModelService;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.PooledConnectionFactory;

@DeclareRoles({ "org.imixs.ACCESSLEVEL.MANAGERACCESS" })
@RunAs("org.imixs.ACCESSLEVEL.MANAGERACCESS")
@ManagedBean
@Stateless()
public class newModel {
	@EJB
	ModelService modelService;
    
    public void importModel() {
    	
		
	 
    	/// open a inputStream of the model file
  		InputStream inputStream;

  		// parse and import the model....
  		try {
  			
  			ClassLoader cl = getClass().getClassLoader();
  			File file = new File(cl.getResource("./proposals.bpmn").getFile());
  			
  			
  		    
  			System.out.println(file);
  			InputStream input = cl.getResourceAsStream("./proposals.bpmn");
  					//new FileInputStream("proposals.bpmn");
  			BPMNModel model = BPMNParser.parseModel(input, "UTF-8");
  			// modelService.importBPMNModel(model);
  			modelService.addModel(model);
  			modelService.saveModel(model);
  	
  	
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
		
	
		// your code goes here....

	}

    private static void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            
           
                System.out.println(f.getName());
           
        } 
    	
    }
}
    
    
    	