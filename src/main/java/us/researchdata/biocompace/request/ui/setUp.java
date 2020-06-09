package us.researchdata.biocompace.request.ui;

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
    	cf = PooledConnectionFactory.builder()
				  .config(ConnectionConfig.builder()
				    .url("ldap://imixs.emilykoranda.com:389")
				    .connectionInitializers(BindConnectionInitializer.builder()
				      .dn("uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com")
				      .credential("YA8Ry29MgF1p8VpUhgap")
				      .build())
				    .build())
				  .min(1)
				  .max(10)
				  .build();
				cf.initialize();
    }
}
