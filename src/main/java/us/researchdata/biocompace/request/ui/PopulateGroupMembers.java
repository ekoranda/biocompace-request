package us.researchdata.biocompace.request.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.jms.ConnectionFactory;

import org.imixs.workflow.engine.UserGroupEvent;





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
        
        // checks if current user is in role
        // this doesn't work
	    if (ctx.isCallerInRole("test")) {
	        customGroups.add("test");
	    }
	    
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
      				// changing to false for now for testing
      				.useStartTLS(false)
      				.connectionInitializers(BindConnectionInitializer.builder()
      					// the credential you are using to authenticate against an LDAP.
      					// CN = common name 
      					// ou = organizational unit
      					// dc = domain component 
      					 * cn = name of the current user
      					 * uid = user name of current user
      					 * .dn("uid=<username> , ou=people, dc=ldaptive, dc=org")
        				.dn("cn=manager,ou=people,dc=ldaptive,dc=org")
        				// password
        				.credential("manager_password")
        				.build())
      				.build())
    			.build(),
  			"dc=ldaptive,dc=org");	
  		SearchResponse response = search.execute("(uid=<username>)");
		LdapEntry entry = response.getEntry();
		String role = entry.getAttribute("isMemberOf");
		
         */
        
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
        	
     /** ConnectionConfig connConfig = new ConnectionConfig("ldap://imixs.emilykoranda.com");
        connConfig.setUseStartTLS(true);
        connConfig.setConnectionInitializer(
        		new BindConnectionInitializer(
        				"o=CO,dc=emilykoranda,dc=com", new Credential("YA8Ry29MgF1p8VpUhgap")));
        		DefaultConnectionFactory cf = new DefaultConnectionFactory(connConfig);
        		SearchExecutor executor = new SearchExecutor();
        		executor.setBaseDn("uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com");
        		/**try {
        			SearchResult result = executor.search(cf, "(uid=proposer1)").getResult();
        			LdapEntry entry = result.getEntry();
        		}catch(LdapException e) {
        			System.out.println(e);
        		}
        */
        /**
        
        
        
      
        /**connConfig.setConnectionInitializer(
          new BindConnectionInitializer(
            "uid=imixs_user,ou=system,o=CO,dc=emilykoranda,dc=com", new Credential("YA8Ry29MgF1p8VpUhgap")));
        DefaultConnectionFactory cf = new DefaultConnectionFactory(connConfig);
        SearchExecutor executor = new SearchExecutor();
        executor.setBaseDn("o=CO,dc=emilykoranda,dc=com");
        try {
			SearchResult result = executor.search(cf, "(uid=*proposer1)", "cn", "sn").getResult();
			String test = result.toString();
			System.out.println(test);
		} catch (LdapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        */
        
        
        //ConnectionConfig connConfig = new ConnectionConfig("ldap://directory.ldaptive.org");
        // add the customGroup to the user's group event
	    userGroupEvent.setGroups(customGroups);

	    return customGroups;
	}
}
