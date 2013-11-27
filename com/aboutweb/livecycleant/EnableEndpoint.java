package com.aboutweb.livecycleant;

import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.aboutweb.livecycleant.AbstractTask;
import com.adobe.idp.dsc.registry.endpoint.CreateEndpointInfo;
import com.adobe.idp.dsc.registry.endpoint.client.EndpointRegistryClient;
import com.adobe.idp.dsc.registry.infomodel.Endpoint;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.dsc.filter.PagingFilter;

/**
 * Enable service end point
 *
 */
public class EnableEndpoint extends AbstractTask {

	private String serviceName;
	private String serviceType;

    /**
     * Ensure specified end point exists and is enabled for service
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	
    	// Make sure service type is valid
    	if(!serviceType.matches("SOAP|REST|EJB|Remoting")){
    		throw new BuildException("SeviceType must be SOAP|REST|EJB|Remoting");
    	}
    	
    	try {
        	ServiceClientFactory scf=super.getServiceClientFactory();

            //Create an EndpointRegistryClient object
            EndpointRegistryClient endPointClient = new EndpointRegistryClient(scf);
                    
            //Retrieve all endpoints
            @SuppressWarnings("unchecked")
			List<Endpoint> allEndpoints = endPointClient.getEndpoints((PagingFilter)null);
            
            //Iterate through the returned list of endpoints to find service connector
            Iterator<Endpoint> iter = allEndpoints.iterator();    
            Endpoint endpoint = null; 
            boolean serviceFound=false;
            while (iter.hasNext()) {
                endpoint = iter.next();
                //Look for an endpoint that belongs to the service
                if (endpoint.getServiceId().matches(serviceName) && endpoint.getConnectorId().matches(serviceType))
                {
                	serviceFound=true;
                	break;
                }
            } 
            if(!serviceFound){
            	log(serviceType + " endpoint not found for " + serviceName + ".  Creating new endpoint");
            	// Create Service
                CreateEndpointInfo e = new CreateEndpointInfo();
                e.setConnectorId(serviceType);
                e.setDescription(serviceType + " endpoint for " + serviceName);
                e.setName(serviceName);
                e.setServiceId(serviceName);
                e.setOperationName("*");
                Endpoint endPoint = endPointClient.createEndpoint(e);
                
                //Enable the Endpoint    
                endPointClient.enable(endPoint);
            	
            } else {
            	// Enable endpoint if it is turned off
            	if(!endpoint.isEnabled())
            	{
            		endPointClient.enable(endpoint);
            	}
            }
    	} catch (Exception e){
    		throw new BuildException("Error checking service configuration: "+e.getMessage());
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
     * Set service type
     * @param service type (SOAP|REST|EJB|Remoting)
     */
    public void setServiceType(String serviceType){
    	this.serviceType=serviceType;
    }

}
