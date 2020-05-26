package us.researchdata.biocompace.request.ui;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.imixs.workflow.engine.UserGroupEvent;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.faces.data.WorkflowEvent;

@Stateless()
@DeclareRoles({ "test"})
public class PopulateGroupMembers {
	@Resource SessionContext ctx;
    public List<String> onUserGroupEvent(@Observes UserGroupEvent userGroupEvent) {
	    List<String> customGroups = new ArrayList<String>();
	    if (ctx.isCallerInRole("test")) {
	        customGroups.add("test");
	    }
	    Principal principal = ctx.getCallerPrincipal();
	    String name = principal.getName();
	    if(name.equals("proposer1")) {
	    	customGroups.add("test");
	    }
	    customGroups.add("addedGroup");
	    userGroupEvent.setGroups(customGroups);
	    System.out.println(ctx.isCallerInRole("test"));
	    return customGroups;
	}
}
