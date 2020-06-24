/*******************************************************************************
 *  Imixs Workflow Technology
 *  Copyright (C) 2003, 2008 Imixs Software Solutions GmbH,  
 *  http://www.imixs.com
 *  
 *  This program is free software; you can redistribute it and/or 
 *  modify it under the terms of the GNU General Public License 
 *  as published by the Free Software Foundation; either version 2 
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 *  General Public License for more details.
 *  
 *  You can receive a copy of the GNU General Public
 *  License at http://www.gnu.org/licenses/gpl.html
 *  
 *  Contributors:  
 *  	Imixs Software Solutions GmbH - initial API and implementation
 *  	Ralph Soika
 *  
 *******************************************************************************/
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
 * This backing bean is an example how to interact with the EntityService to
 * manage ItemCollections as instances of Entities
 * <p>
 * The bean provides an ItemCollectionAdapter to simplify to access properties
 * of the ItemCollection from an JSP page.
 * <p>
 * See also the TeamPlugin. This plugin maps the team selection into a team item
 * of the workitem during processing.
 * 
 * @see team.xhtml, teamlist.xhtml
 * 
 * @author rsoika
 * 
 */


@Named
@SessionScoped
public class mail implements Serializable {

	private static final long serialVersionUID = 1L;

	public void onWorkflowEvent(@Observes WorkflowEvent workflowEvent) throws AccessDeniedException {
		PooledConnectionFactory cf = setUp.cf;
		List<String> reviewerEmail = new ArrayList<String>();
		List<String> managerEmail = new ArrayList<String>();
		String proposerEmail = null;
	    
	    try {	
	    	// read from config.properties file
	    	Properties prop = new Properties();
	    	prop.load(new FileInputStream("standalone/configuration/config.properties"));
	    	String bindDN = prop.getProperty("Base_DN");
	    	// get the ldap names for the proposer, reviewer, and manager groups
	    	String proposerName =prop.getProperty("Proposer_Group");
	    	String reviewerName = prop.getProperty("Reviewer_Group");
	    	String managerName = prop.getProperty("Manager_Group");
	    	
	    	// set the filters for finding reviewer email addresses and manager email addresses
	    	String ldapFilter = "(|(isMemberOf=" + reviewerName + ")(isMemberOf=" + managerName + "))";
	    	//TODO: make this configurable
	    	String proposerFilter = "(uid=" + workflowEvent.getWorkitem().getItemValue("$creator").get(0) + ")";
	    	
	    	// ldap query
	    	SearchOperation search = new SearchOperation(cf, bindDN);
	    	SearchResponse response = search.execute(ldapFilter, "mail", "uid", "isMemberOf");
	    	SearchResponse proposerResponse = search.execute(proposerFilter, "mail");
	    	
	    	Collection<LdapEntry> entries = response.getEntries();
	    	LdapEntry proposerEntry = proposerResponse.getEntry();
	    	proposerEmail = proposerEntry.getAttribute("mail").getStringValue();
	    	System.out.println(proposerEmail);
	    	
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
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		
		
		
		workflowEvent.getWorkitem().setItemValue("group.reviewerEmail", reviewerEmail);
		
		
		
		workflowEvent.getWorkitem().setItemValue("group.proposerEmail", proposerEmail);
		
		
		
      	workflowEvent.getWorkitem().setItemValue("group.managerEmail", managerEmail);

		
		
		

	}

	

}


