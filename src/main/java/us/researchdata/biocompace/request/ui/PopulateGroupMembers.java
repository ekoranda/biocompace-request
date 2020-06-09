package us.researchdata.biocompace.request.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
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
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.SimpleBindRequest;
import org.ldaptive.SingleConnectionFactory;








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
    	
    	// list that contains new groups for the current user
        List<String> customGroups = new ArrayList<String>();
     // get the name of the current user
	    Principal principal = ctx.getCallerPrincipal();
	    String name = principal.getName();
	    String uid = "(uid=" + name + ")";
    	
    	/**SearchOperation search = new SearchOperation(
    			  DefaultConnectionFactory.builder()
    			    .config(ConnectionConfig.builder()
    			      .url("ldap://imixs.emilykoranda.com:389")
    			      //.useStartTLS(true) later
    			      .useStartTLS(false)
    			      .connectionInitializers(BindConnectionInitializer.builder()
    			        .dn("uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com")
    			        .credential("YA8Ry29MgF1p8VpUhgap")
    			        .build())
    			      .build())
    			    .build(),
    			  "ou=people,o=CO,dc=emilykoranda,dc=com");
    			try {
    				
    				SearchResponse response = search.execute(uid, "isMemberOf");
    				for (LdapEntry entry : response.getEntries()) {
    					System.out.println(entry.getAttribute("isMemberOf"));
    					LdapAttribute attribute = entry.getAttribute("isMemberOf");
    					String roleName;
    					roleName = "group.";
    					roleName = roleName.concat(attribute.getStringValue());
    					roleName = roleName.replace(" ", "");
    					customGroups.add(roleName);
    				}
    			}catch(LdapException e) {
    				
    			}
    			
    	
	    */
	    	
    			/**PooledConnectionFactory cf = PooledConnectionFactory.builder()
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
    					
    					try {
    					SearchOperation search = new SearchOperation(cf, "ou=people,o=CO,dc=emilykoranda,dc=com");
    					SearchResponse response = search.execute(uid, "isMemberOf");
    					LdapEntry entry = response.getEntry();
    					LdapAttribute attribute = entry.getAttribute("isMemberOf");
    					String roleName;
    					roleName = "group.";
    					roleName = roleName.concat(attribute.getStringValue());
    					roleName = roleName.replace(" ", "");
    					customGroups.add(roleName);
    					System.out.println(roleName);
    					
    					}catch (LdapException e){
    						cf.close();
    					}
    					
    					*/
    			
	    PooledConnectionFactory cf = setUp.cf;
    	
	    
	    try {
			SearchOperation search = new SearchOperation(cf, "ou=people,o=CO,dc=emilykoranda,dc=com");
			SearchResponse response = search.execute(uid, "isMemberOf");
			LdapEntry entry = response.getEntry();
			LdapAttribute attribute = entry.getAttribute("isMemberOf");
			String roleName;
			roleName = "group.";
			roleName = roleName.concat(attribute.getStringValue());
			roleName = roleName.replace(" ", "");
			System.out.println(roleName);
			
			}catch (LdapException e){
				cf.close();
			}
			
			
		
    	
    	
    	
        
        
       
	    
	    
	    
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
        
        
        // add the customGroup to the user's group event
	    userGroupEvent.setGroups(customGroups);

	    return customGroups;
	}
}


