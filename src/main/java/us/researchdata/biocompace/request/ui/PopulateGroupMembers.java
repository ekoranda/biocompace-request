package us.researchdata.biocompace.request.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.Identity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedBean;

import java.util.Properties;


import org.imixs.workflow.engine.UserGroupEvent;
import org.imixs.workflow.faces.util.LoginController;
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

@ManagedBean
@Stateless()
public class PopulateGroupMembers {
	

	@Resource SessionContext ctx;
    public List<String> onUserGroupEvent(@Observes UserGroupEvent userGroupEvent) {
    	
    	
    	
    	// list that contains new groups for the current user
        List<String> customGroups = new ArrayList<String>();
     // get the name of the current user
	    Principal principal = ctx.getCallerPrincipal();
	    String name = principal.getName();
	    String ldapFilter = "(uid=" + name + ")";
    
	    
    			
	    PooledConnectionFactory cf = setUp.cf;
    	
	    
	    try {	
	    	
	    	Properties prop = new Properties();
	    	prop.load(new FileInputStream("standalone/configuration/config.properties"));
	    	String bindDN = prop.getProperty("Base_DN");
	    	String filter = prop.getProperty("filter");
	    	
			SearchOperation search = new SearchOperation(cf, bindDN);
			try {
			SearchResponse response = search.execute(ldapFilter, filter);
			LdapEntry entry = response.getEntry();
			// make multiple groups or if there are no groups
			
			LdapAttribute attribute = entry.getAttribute(filter);
			ArrayList<String> roleNames = new ArrayList<String>();
			if ( ! attribute.getStringValues().isEmpty()) {
				roleNames.addAll(attribute.getStringValues());
				
				for (String role: roleNames) {
					String roleName;
					roleName = "group.";
					roleName = roleName.concat(role);
					roleName = roleName.replace(" ", "");
					customGroups.add(roleName);
				}
			}
		}catch(NullPointerException e) {
		}
			
		}catch (LdapException e){
				cf.close();
		}catch(Exception e){
				e.printStackTrace();
		}
			
			
		

	    /**
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
        
        */
	    
        
        // add the customGroup to the user's group event
	    userGroupEvent.setGroups(customGroups);
	    return customGroups;
	}
    
    
    
    
}


