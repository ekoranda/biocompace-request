package us.researchdata.biocompace.request.ui;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.imixs.workflow.faces.data.ViewController;

/**
 * Simple example for a search controller searching the Imixs Fulltext Index
 * 
 * @author rsoika
 */



@Named
@SessionScoped
public class EditController extends ViewController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String input;
	private String username;


	private static Logger logger = Logger.getLogger(EditController.class.getName());

	@Override
	@PostConstruct
	public void init() {
		super.init();
		this.setQuery("(type:\"workitem\")");
		this.setSortBy("$modified");
		this.setSortReverse(true);
		this.setLoadStubs(false);
		username = "mkoranda";
		
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
		System.out.println(input);
		System.out.println("made it here");
	}



	public void search() {
		this.setQuery("((type:\"workitem\") AND ($creator:"+input+"))");

		logger.info("serach query=" + this.getQuery());
		System.out.println("search");
		System.out.println(input);
		

		
	}
	
	public String url() {
		String url = "/pages/workflow/workitem.xhtml?id=";
		return url;
				
	}
	
	

}

