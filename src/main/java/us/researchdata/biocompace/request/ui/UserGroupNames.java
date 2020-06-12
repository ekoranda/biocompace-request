package us.researchdata.biocompace.request.ui;

import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;




@ManagedBean

public class UserGroupNames {

	private String proposerName;
	private String managerName;
	private String reviewerName;

    /**
     * Adds the different types to the array List
     */

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
	        
	        reviewerName = reviewerName.concat(prop.getProperty("Reviewer_Group"));
	        reviewerName = reviewerName.replace(" ", "");
	        
	        managerName = managerName.concat(prop.getProperty("Manager_Group"));
	        managerName = managerName.replace(" ", "");
	        
	        
	        
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
