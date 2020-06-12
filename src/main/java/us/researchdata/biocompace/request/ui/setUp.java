package us.researchdata.biocompace.request.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.PooledConnectionFactory;

@Startup
@Singleton
public class setUp {
	static PooledConnectionFactory cf;
    @PostConstruct
    public void start() {
    	
    	Properties prop = new Properties();

	    try {
	        prop.load(new FileInputStream("standalone/configuration/config.properties"));
	        String bindDN = prop.getProperty("Bind_DN");
	        String password = prop.getProperty("Password");
	        String URL = prop.getProperty("URL");
	        cf = PooledConnectionFactory.builder()
					  .config(ConnectionConfig.builder()
					    .url(URL)
					    .connectionInitializers(BindConnectionInitializer.builder()
					      .dn(bindDN)
					      .credential(password)
					      .build())
					    .build())
					  .min(5)
					  .max(10)
					  .build();
					cf.initialize();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
    }
}

