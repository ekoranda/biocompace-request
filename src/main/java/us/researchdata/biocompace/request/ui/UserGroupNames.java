package us.researchdata.biocompace.request.ui;

import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;




@ManagedBean
/**
 * Reads from .properties file to read configured group names
 * and sets the group names to be used in the application
 * @author ekoranda1
 *
 */
public class UserGroupNames {

	private String proposerName;
	private String managerName;
	private String reviewerName;

    @PostConstruct
    
    
    public void init() {
    	Properties prop = new Properties();
    	proposerName = "group.";
    	reviewerName = "group.";
    	managerName = "group.";

	    try {
	        prop.load(new FileInputStream("standalone/configuration/config.properties"));
	        proposerName = proposerName.concat(prop.getProperty("Proposer_Group"));
	        proposerName = proposerName.replace(" ", "");
	        //proposerName = "group.proposer";
	        
	        reviewerName = reviewerName.concat(prop.getProperty("Reviewer_Group"));
	        reviewerName = reviewerName.replace(" ", "");
	        //reviewerName = "group.reviewer";
	        
	        managerName = managerName.concat(prop.getProperty("Manager_Group"));
	        managerName = managerName.replace(" ", "");
	        //managerName = "group.manager";
	        
	        
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        
    }
 
    public String getProposerName() {
        return proposerName;
    }
    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

 

}
