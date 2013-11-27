package com.aboutweb.livecycleant;

import com.aboutweb.livecycleant.AbstractTask;
import org.apache.tools.ant.BuildException;
import java.io.FileInputStream;
import com.adobe.idp.applicationmanager.application.ApplicationStatus;
import com.adobe.idp.applicationmanager.client.ApplicationManager;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.Document;

/**
 * Import a LiveCycle archive
 *
 */
public class ImportArchive extends AbstractTask {

	private String file;
	
    /**
     * Import a LiveCycle archive
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	FileInputStream fileApp;
    	int status;
    	
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	
        // Read the LCA file  
    	try {
    		fileApp = new FileInputStream(file); 
    	} catch (Exception e){
    		throw new BuildException("Error reading LiveCycle archive file:"+e.getMessage());
    	}
	    Document lcApp = new Document(fileApp); 
                              
        //Create an ApplicationManager object 
        ApplicationManager appManager = new ApplicationManager(scf);  
         
        //Import the application 
       	try {
	        ApplicationStatus appStatus = appManager.importApplicationArchive(lcApp); 
	        status = appStatus.getStatusCode(); 
    	} catch (Exception e){
    		throw new BuildException("Error importing LiveCycle archive file:"+e.getMessage());
    	}

        //Determine if the application was successfully deployed 
        if (status==1) 
                log("The LiveCycle archive was successfully imported"); 
        else 
        	throw new BuildException("The LiveCycle archive failed to import properly. The status is "+status); 
    }
    

    /**
     * Set archive file to import
     * @param full path to archive file
     */
    public void setFile(String file){
    	this.file=file;
    }	
    
}
