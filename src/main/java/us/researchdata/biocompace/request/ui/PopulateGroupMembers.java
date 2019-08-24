package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;

import org.imixs.workflow.engine.UserGroupEvent;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.faces.data.WorkflowEvent;

public class PopulateGroupMembers {
	static String creator;
	
    private static Logger logger = Logger.getLogger(PopulateGroupMembers.class.getName());

    public void onWorkflowEvent(@Observes WorkflowEvent workflowEvent) throws AccessDeniedException {
    	creator = workflowEvent.getWorkitem().getItemValueString("$creator");
	    

	}
	public void onUserGroupEvent(@Observes UserGroupEvent userGroupEvent) {
	    List<String> customGroups = new ArrayList<String>();
	    String userID = userGroupEvent.getUserId();
	    if(userID.equals("reviewer")) {
	    	customGroups.add("reviwer");
	    }
	   // if(userID.equals("user")) {
	    	//customGroups.add("user");
	   // }
	    if(userID.equals("administrator")) {
	    	customGroups.add("administrator");
	    }
	    if(userID.equals(creator)) {
	    	customGroups.add("creator");
	    }
	 
	    
	    // getUserID();
	    userGroupEvent.setGroups(customGroups);
	}
}
