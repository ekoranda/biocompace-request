package us.researchdata.biocompace.request.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.imixs.workflow.engine.UserGroupEvent;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.BindOperation;
import org.ldaptive.BindResponse;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.SimpleBindRequest;
import org.ldaptive.SingleConnectionFactory;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;






/**
 * Reads from a LDAP file to add roles to the current user
 * @author ekoranda1
 *
 */
@Stateless()
@DeclareRoles( "test")
public class PopulateGroupMembers {
	@Resource SessionContext ctx;
    public List<String> onUserGroupEvent(@Observes UserGroupEvent userGroupEvent) {
    	
    	
    			
    	
    	
    	/**try {
    	SingleConnectionFactory cf = new SingleConnectionFactory("ldap://imixs.emilykoranda.com:389/dc=emilykoranda,dc=com");
    	cf.initialize();
    	BindOperation bind = new BindOperation(cf);
    	BindResponse res = bind.execute(SimpleBindRequest.builder()
    			
    	  .dn("uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com")
    	  .password(new Credential("YA8Ry29MgF1p8VpUhgap"))   
    	  
    	  .build());

    	if (res.isSuccess()) {
    	  // bind succeeded
    		System.out.println("res is successful");
    	} else {
    		System.out.println("res is not successful");
    	  // bind failed
    	}
    	// use the connection factory to perform operations as uid=dfisher
    	cf.close();
    	}catch(LdapException e) {
    		
    		System.out.println("ERROR MADE IT HERE");
    		e.printStackTrace();
    	}
    	
    	
    	*/
		
    	
    	
    	
        // list that contains new groups for the current user
        List<String> customGroups = new ArrayList<String>();
        
       
	    
	    // get the name of the current user
	    Principal principal = ctx.getCallerPrincipal();
	    String name = principal.getName();
	    
	    // hardcode into ldap directory to do a search for users
	    
    	// create buffer for csvFile
    	String csvFile = "users.txt";
        String line = "";
        String csvSplitBy = ",";
	   
	    // buffer through the csv file line by line to iterate over each user
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] users = line.split(csvSplitBy);
                String roleName;
                
                // find the role of the current user and add it to their groups list
                if(users[0].equals(name)){
                	roleName = "group.";
                	roleName = roleName.concat(users[1]);
                	roleName = roleName.replace(" ", "");
                	customGroups.add(roleName);
                }
                
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
      
        
      /**SearchOperation search = new SearchOperation(
    		  DefaultConnectionFactory.builder()
        		    .config(ConnectionConfig.builder()
        		      .url("ldap://imixs.emilykoranda.com")
        		      .useStartTLS(true)
        		      .connectionInitializers(BindConnectionInitializer.builder()
        		        .dn("uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com")
        		        .credential("YA8Ry29MgF1p8VpUhgap")
        		        .build())
        		      .build())
        		    .build(),
        		  "dc=emilykoranda,dc=com");
        		try {
					SearchResult response = search.execute("(uid=*proposer1)");
					LdapEntry entry = response.getEntry();
					LdapAttribute role = entry.getAttribute("isMemberOf");
					String group = role.getStringValue();
					System.out.println(group);
				} catch (LdapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("EXCEPTION FAILED");
				}
   
        
        */
        
        
        
        // add the customGroup to the user's group event
	    userGroupEvent.setGroups(customGroups);

	    return customGroups;
	}
}
