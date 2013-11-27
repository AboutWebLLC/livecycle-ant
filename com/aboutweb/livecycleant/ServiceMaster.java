package com.aboutweb.livecycleant;

import java.util.List;
import com.aboutweb.livecycleant.AbstractTask;
import com.adobe.idp.dsc.registry.infomodel.ServiceConfiguration;
import com.adobe.idp.dsc.registry.service.client.ServiceRegistryClient;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import org.apache.tools.ant.BuildException;

/**
 * Ensure only a specific version of a service is running
 *
 */
public class ServiceMaster extends AbstractTask {

	private String serviceName;
	private int majorVersion;
	private int minorVersion;
	
    /**
     * Ensure only a specific version of a service is running
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	boolean masterFound=false;
    	
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	
    	//Create a ServiceRegistryClient object to find the service
    	ServiceRegistryClient srClient = new ServiceRegistryClient(scf);
    	try {
    		@SuppressWarnings("unchecked")
			List<ServiceConfiguration> scList=srClient.getServiceConfigurations();
    		for(ServiceConfiguration sc: scList ) {
    			if(sc.getServiceId().equals(serviceName)){
    				if(sc.getMajorVersion()==majorVersion && sc.getMinorVersion()==minorVersion){
    					masterFound=true;
    					System.out.println("Found master service [" + serviceName + "/" + sc.getMajorVersion() + "." + sc.getMinorVersion() + "]");
    					if(sc.isStopped()){
    						srClient.start(sc);
        					System.out.println("Started master service [" + serviceName + "/" + sc.getMajorVersion() + "." + sc.getMinorVersion() + "]");    						    						
    					}
    				} else {
    					System.out.println("Found non master service [" + serviceName + "/" + sc.getMajorVersion() + "." + sc.getMinorVersion() + "]");
    					if(sc.isRunning()){
    						srClient.stop(sc);
        					System.out.println("Stopped non master service [" + serviceName + "/" + sc.getMajorVersion() + "." + sc.getMinorVersion() + "]");    						
    					}
    				}
    			}
    		 }
    		// The specified version of the service must be present
    		if(!masterFound){
    			throw new BuildException("Specified service version not found for [" + serviceName + "/" + majorVersion + "." + minorVersion + "]");
    		}
    	} catch (Exception e){
    		throw new BuildException("Error setting master service:"+e.getMessage());
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


    
	
}
