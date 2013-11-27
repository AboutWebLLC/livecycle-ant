package com.aboutweb.livecycleant;

import com.aboutweb.livecycleant.AbstractTask;
import org.apache.tools.ant.BuildException;
import java.io.File;
import com.adobe.idp.applicationmanager.application.ApplicationManagerException;
import com.adobe.idp.applicationmanager.client.ApplicationConfigurationManager;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.Document;

/**
 * Set the runtime configuration for a LiveCycle application
 *
 */
public class RuntimeConfig extends AbstractTask {

	// Always overwrite existing runtime settings
	private static final boolean OVERWRITE_EXISTING=true;

	private String applicationName;
	private int majorVersion;
	private int minorVersion;
	private String runtimeConfigFile;
	
    /**
     * Set the runtime configuration
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	
    	//Create an ApplicationManagerServiceClient object
    	ApplicationConfigurationManager acmClient = new ApplicationConfigurationManager(scf);
    	try {
    		// Set up runtime configuration file to send to service
			File settingsFile = new File(runtimeConfigFile);		
			// Make sure the file exists and is readable
			if(!settingsFile.exists()){
	    		throw new BuildException("The specified runtime settings file does not exist or is not accessable");
			}
			if(!settingsFile.canRead()){
	    		throw new BuildException("The specified runtime settings file can not be read");
			}
			Document runtimeSettings = new Document(settingsFile,false);

			// Service call to import runtime value
    		acmClient.importRuntimeValues(applicationName, majorVersion, minorVersion, runtimeSettings, OVERWRITE_EXISTING);
    		
    		// Log success message for feedback
    		log("Successfully updated runtime configuration for [" + applicationName + "/" + majorVersion + "." + minorVersion + "]");
    		
    	} catch (ApplicationManagerException e){
    		throw new BuildException("Error applying runtime configuration:"+e.getMessage());
    	}
    }
    
    /**
     * Set application name
     * @param application name to configure
     */
    public void setApplicationName(String applicationName){
    	this.applicationName=applicationName;
    }

    /**
     * Set application major version
     * @param application major version
     */
    public void setMajorVersion(int majorVersion){
    	this.majorVersion=majorVersion;
    }

    /**
     * Set application minor version
     * @param application minor version
     */
    public void setMinorVersion(int minorVersion){
    	this.minorVersion=minorVersion;
    }

    /**
     * Set application run time configuration file
     * @param full path to application configuration file
     */
    public void setRuntimeConfigFile(String runtimeConfigFile){
    	this.runtimeConfigFile=runtimeConfigFile;
    }	
    
}
