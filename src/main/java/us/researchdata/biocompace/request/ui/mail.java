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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.imixs.workflow.ItemCollection;
import org.imixs.workflow.engine.DocumentService;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.faces.data.WorkflowEvent;
import org.imixs.workflow.faces.util.LoginController;

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


	@EJB
	DocumentService documentSerivce;

	/**
	 * Translate team ref into the team name (_teamName)
	 * 
	 * @param workflowEvent
	 * @throws AccessDeniedException
	 */


	public void onWorkflowEvent(@Observes WorkflowEvent workflowEvent) throws AccessDeniedException {
		if (workflowEvent.getEventType() == WorkflowEvent.WORKITEM_BEFORE_PROCESS) {
			ArrayList<String> emailRecipients = new ArrayList<String>();
			emailRecipients.add("ekoranda@researchdata.us");
			
			String testEmail = "ekoranda@researchdata.us";

			
		}
		System.out.println("HERE");
		
		workflowEvent.getWorkitem().setItemValue("mailRecipients", "ekoranda@researchdata.us");
		

	}

	

}


