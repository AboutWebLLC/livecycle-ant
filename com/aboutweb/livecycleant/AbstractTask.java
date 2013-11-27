package com.aboutweb.livecycleant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactoryProperties;

/**
 * Abstract task for all LiveCycle Ant tasks
 * Loads in common configuration file and provides method to
 *  set up connection factory for LiveCycle server
 *
 */
public abstract class AbstractTask extends Task {
	
    // Since we are using SOAP the server type doesn't affect the connection
	private static final String SERVER_TYPE="Jboss";
	
	// LiveCycle properties file, using same structure as Adobe Ant task property file
	private String propFilename = "lc.properties"; 
	
	
    /**
     * Read the properties file and create a ServiceClientFactory to connect to LiveCycle server
     * @exception BuildException if an error occurs.
     */
	public ServiceClientFactory getServiceClientFactory() {
		ServiceClientFactory serviceClientFactory=null;
    	Properties p = new Properties();
    	
    	// Set up default for timeout parameter which is optional (20 minutes)
    	p.put("timeout", "1200000");
 
    	// Load properties from specified file
    	try {
    		InputStream in = new FileInputStream(propFilename);
	    	p.load(in);
	    	in.close();
    	} catch (Exception e) {
    		throw new BuildException("Could not read LiveCycle properties file, exiting");
    	}

    	// Check to see if requires properties where specified in configuration file
    	if(p.getProperty("serverUrl")==null){
    		throw new BuildException("serverUrl not specified in LiveCycle properties file");
    	}
    	if(p.getProperty("username")==null){
    		throw new BuildException("username not specified in LiveCycle properties file");
    	}
    	if(p.getProperty("password")==null){
    		throw new BuildException("password not specified in LiveCycle properties file");
    	}
   	
    	// Set up SOAP connection factory
        try {
	        //Set connection properties required to invoke LiveCycle using SOAP mode                            
	        Properties connectionProps = new Properties();
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_DEFAULT_SOAP_ENDPOINT, p.getProperty("serverUrl"));
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_TRANSPORT_PROTOCOL,ServiceClientFactoryProperties.DSC_SOAP_PROTOCOL);
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_SERVER_TYPE, SERVER_TYPE);
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_CREDENTIAL_USERNAME, p.getProperty("username"));
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_CREDENTIAL_PASSWORD, p.getProperty("password"));
	        connectionProps.setProperty(ServiceClientFactoryProperties.DSC_REQUEST_TIMEOUT, p.getProperty("timeout"));

	        // Create the ServiceClientFactory
	        serviceClientFactory = ServiceClientFactory.createInstance(connectionProps);
	        
        } catch (Exception e) {
        	throw new BuildException("Error creating connection factory: "+e.getMessage());
        }
		return serviceClientFactory;
	}

    /**
     * Set LiveCycle property file
     * @param full path to LiveCycle property file
     */
    public void setPropertiesFile(String filename){
    	this.propFilename=filename;
    }


}
