
package us.researchdata.biocompace.request.ui;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.faces.data.WorkflowEvent;

import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;

/**
 * Reads from a ldap to get the email attributes and add the email addresses
 * To their corresponding email list based off of that user's organizational group
 * Those email lists can be accessed in the model. 
 * 
 * @author ekoranda
 */



@Named
@SessionScoped
public class mail implements Serializable {

	private static final long serialVersionUID = 1L;

	public void onWorkflowEvent(@Observes WorkflowEvent workflowEvent) throws AccessDeniedException {
		
		// get the ldap bind that was set up at deployment
		PooledConnectionFactory cf = setUp.cf;
		
		// email lists for organizational groups
		List<String> reviewerEmail = new ArrayList<String>();
		List<String> managerEmail = new ArrayList<String>();
		String proposerEmail = null;
	    
	    try {	
	    	// read from config.properties file
	    	Properties prop = new Properties();
	    	prop.load(new FileInputStream("standalone/configuration/config.properties"));
	    	// get ldap configurattion details
	    	String bindDN = prop.getProperty("Base_DN");
	    	String filter = prop.getProperty("ldapFilter");
	    	// get the ldap names for the proposer, reviewer, and manager groups
	    	String proposerName =prop.getProperty("Proposer_Group");
	    	String reviewerName = prop.getProperty("Reviewer_Group");
	    	String managerName = prop.getProperty("Manager_Group");

	    	
	    	// set the filters for finding reviewer email addresses and manager email addresses
	    	String ldapFilter = "(|(isMemberOf=" + reviewerName + ")(isMemberOf=" + managerName + "))";
	    	// set the filter for getting the email address of the creator of the workitem
	    	String proposerFilter = "(" + filter + "=" + workflowEvent.getWorkitem().getItemValue("$creator").get(0) + ")";
	    	
	    	// ldap search
	    	SearchOperation search = new SearchOperation(cf, bindDN);
	    	SearchResponse response = search.execute(ldapFilter, "mail", "uid", "isMemberOf");
	    	SearchResponse proposerResponse = search.execute(proposerFilter, "mail");
	    	
	    	Collection<LdapEntry> entries = response.getEntries();
	    	LdapEntry proposerEntry = proposerResponse.getEntry();
	    	proposerEmail = proposerEntry.getAttribute("mail").getStringValue();
	    	
			//LdapEntry entry = response.getEntry();
	    	for (LdapEntry user: entries) {

	    		Collection<String>  groupNames = user.getAttribute("isMemberOf").getStringValues();

	    		//String email = user.getAttribute("email").getStringValue();
	    		if(groupNames.contains(reviewerName)) {
	    			//TODO: what if user has more than one email address
	    			reviewerEmail.add(user.getAttribute("mail").getStringValue());
	    		}
	    		if(groupNames.contains(managerName)) {
	    			managerEmail.add(user.getAttribute("mail").getStringValue());
	    		}	
	    	}
	    	
	    	
			
			
	    	
	    }catch(LdapException e) {
	    	e.printStackTrace();
	    }catch(NullPointerException e) {
	    	System.out.println("null pointer exception");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	   
		
		
		
		
		workflowEvent.getWorkitem().setItemValue("group.reviewerEmail", reviewerEmail);
		
		
		
		workflowEvent.getWorkitem().setItemValue("group.proposerEmail", proposerEmail);
		
		
		
      	workflowEvent.getWorkitem().setItemValue("group.managerEmail", managerEmail);

		
		
		

	}

	

}


