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
	    
	    // uses pooled connection from set up
	    PooledConnectionFactory cf = setUp.cf;
    	
	    
	    try {	
	    	// read ldap configuration from .properties file
	    	Properties prop = new Properties();
	    	prop.load(new FileInputStream("standalone/configuration/config.properties"));
	    	// get ldap properties
	    	String bindDN = prop.getProperty("Base_DN");
	    	String groupAttribute = prop.getProperty("groupAttribute");
	    	String ldapFilter = prop.getProperty("ldapFilter");
	    	// get group name information
	    	String proposerName =prop.getProperty("Proposer_Group");
	    	String reviewerName = prop.getProperty("Reviewer_Group");
	    	String managerName = prop.getProperty("Manager_Group");
	        managerName = managerName.replace(" ", "");
	        
	    	// ldap filter to query through the ldap with
	    	ldapFilter = "(" + ldapFilter + "=" + name + ")";
	    	// create a new ldap search
			SearchOperation search = new SearchOperation(cf, bindDN);
			try {
				SearchResponse response = search.execute(ldapFilter, groupAttribute);
				LdapEntry entry = response.getEntry();		
				// get the group attribute from the search
				LdapAttribute attribute = entry.getAttribute(groupAttribute);
				ArrayList<String> roleNames = new ArrayList<String>();
				if ( ! attribute.getStringValues().isEmpty()) {
					roleNames.addAll(attribute.getStringValues());
					for (String role: roleNames) {
						// add the groups from the group attribute to the custom groups list 						
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
 
        // add the customGroup to the user's group event
	   
	    userGroupEvent.setGroups(customGroups);
	    return customGroups;
	}
    
    
    
    
}



