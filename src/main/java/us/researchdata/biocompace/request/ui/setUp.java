package us.researchdata.biocompace.request.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.imixs.workflow.bpmn.BPMNModel;
import org.imixs.workflow.bpmn.BPMNParser;
import org.imixs.workflow.engine.ModelService;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.PooledConnectionFactory;



@DeclareRoles({ "org.imixs.ACCESSLEVEL.MANAGERACCESS" })
@RunAs("org.imixs.ACCESSLEVEL.MANAGERACCESS")
@Startup
@Singleton
public class setUp {
	@EJB
	ModelService modelService;
	static PooledConnectionFactory cf;
    @PostConstruct
 
    public void start() {
    	
    	Properties prop = new Properties();

	    try {
	        prop.load(new FileInputStream("standalone/configuration/config.properties"));
	        String bindDN = prop.getProperty("Bind_DN");
	        String password = prop.getProperty("Password");
	        String URL = prop.getProperty("URL");
	        int maxSize = Integer.valueOf(prop.getProperty("maxSize"));
	        int minSize = Integer.valueOf(prop.getProperty("minSize"));

	        cf = PooledConnectionFactory.builder()
					  .config(ConnectionConfig.builder()
					    .url(URL)
					    .connectionInitializers(BindConnectionInitializer.builder()
					      .dn(bindDN)
					      .credential(password)
					      .build())
					    .build())
					  .min(minSize)
					  .max(maxSize)
					  .build();
					cf.initialize();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	   
		
	 // open a inputStream of the model file

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
	    
    	
    }
    
 
    	
    
    


