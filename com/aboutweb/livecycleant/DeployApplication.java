package com.aboutweb.livecycleant;

import org.apache.tools.ant.BuildException;
import com.aboutweb.livecycleant.AbstractTask;
import com.adobe.livecycle.applicationmanager.client.ApplicationManagerClient;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;

/**
 * Deploy a LiveCycle application
 *
 */
public class DeployApplication extends AbstractTask {

	private String applicationName;
	private String version;

    /**
     * Deploys a LiveCycle application
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	ServiceClientFactory scf=super.getServiceClientFactory();

    	try {
    		ApplicationManagerClient amClient=new ApplicationManagerClient(scf);
    		amClient.deployApplication(applicationName,version);
    		
    		// Log success message for feedback
    		log("Successfully deployed application: " + applicationName + "/" + version);

    	} catch (Exception e){
    		throw new BuildException("Error deploying application:"+e.getMessage());
    	}
    }
    
    /**
     * Set application name
     * @param application name
     */
    public void setApplicationName(String applicationName){
    	this.applicationName=applicationName;
    }   

    /**
     * Set application version
     * @param application version
     */
    public void setVersion(String version){
    	this.version=version;
    }   

}
