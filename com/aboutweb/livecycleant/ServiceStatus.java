package com.aboutweb.livecycleant;

import com.aboutweb.livecycleant.AbstractTask;
import com.adobe.idp.dsc.registry.infomodel.ServiceConfiguration;
import com.adobe.idp.dsc.registry.service.client.ServiceRegistryClient;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;  


public class ServiceStatus extends AbstractTask {

	private String serviceName;
	private String result;
	
    /**
     * Gets the status of the specified service includes:
     *  component version
     *  major version
     *  minor version
     *  run status
     *  Sets the result string into the specified Ant variable
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	// Make sure result argument specified
    	if(result==null){
    		throw new BuildException("result argument required");
    	}
    	
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	//Create a ServiceRegistryClient object to find the service
    	ServiceRegistryClient srClient = new ServiceRegistryClient(scf);

    	try {
    		ServiceConfiguration sc=srClient.getHeadServiceConfiguration(serviceName);
    		StringBuilder sb = new StringBuilder();
    		sb.append("componentVersion="+sc.getComponentVersion());
    		sb.append(";majorVersion="+sc.getMajorVersion());
    		sb.append(";minorVersion="+sc.getMinorVersion());
    		sb.append(";running="+sc.isRunning());
    		Project project = getProject();  
    		project.setProperty(result,sb.toString());  
    		// Log success message for feedback
    		log("Successfully retrieved service status for [" + serviceName + "]");
    	} catch (Exception e){
    		throw new BuildException("Error getting service status:"+e.getMessage());
    	}
    }

    /**
     * Set service name
     * @param service name
     */
    public void setServiceName(String serviceName){
    	this.serviceName=serviceName;
    }

    /**
     * Set Ant result variable
     * @param ant result variable
     */
    public void setResult(String result){
    	this.result=result;
    }

    
	
}
