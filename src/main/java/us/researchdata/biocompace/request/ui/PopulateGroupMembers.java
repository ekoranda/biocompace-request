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
        
        
        // add the customGroup to the user's group event
	    userGroupEvent.setGroups(customGroups);

	    return customGroups;
	}
}
