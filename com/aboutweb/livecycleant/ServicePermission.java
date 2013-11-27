package com.aboutweb.livecycleant;

import com.aboutweb.livecycleant.AbstractTask;
import com.adobe.livecycle.usermanager.client.AuthorizationManagerServiceClient;
import com.adobe.livecycle.usermanager.client.DirectoryManagerServiceClient;
import java.util.ArrayList;
import java.util.List;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.dsc.registry.infomodel.Service;
import com.adobe.idp.dsc.registry.security.client.SecurityProfileManagerClient;
import com.adobe.idp.dsc.registry.service.client.ServiceRegistryClient;
import com.adobe.idp.um.api.infomodel.Principal;
import org.apache.tools.ant.BuildException;


/**
 * Give specified principal specified permissions on a service
 * Permissions are set as a comma delimited list
 *
 */
public class ServicePermission extends AbstractTask {

	private String serviceName;
	private String principalName;
	private String domainName;
	private String permissionNames;
	
    /**
     * Give specified principal specified permissions on a service
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	
    	try {
            //Create an SecurityProfileManagerClient object
            SecurityProfileManagerClient secClient = new SecurityProfileManagerClient(scf);
                             
            AuthorizationManagerServiceClient aClient = new AuthorizationManagerServiceClient(scf);

            // Lookup principal id
            DirectoryManagerServiceClient dirClient = new DirectoryManagerServiceClient(scf);
            Principal pri=dirClient.findPrincipal(domainName,principalName);
            if(pri == null){
            	throw new BuildException("Principal not found:"+principalName);
            }
            String principalID=pri.getOid();
            
            // Lookup service id
        	//Create a ServiceRegistryClient object to find the service
        	ServiceRegistryClient srClient = new ServiceRegistryClient(scf);
        	Service ser=srClient.getService(serviceName);
            if(ser == null){
            	throw new BuildException("Specified LiveCycle service not found: " + serviceName);
            }
        	String serviceUuid=ser.getUuid();
        	
            // Lookup permission ids
        	String[] perNameArr = permissionNames.split(",");
            List<String> permissionList=new ArrayList<String>();
            for(int i=0;i<perNameArr.length;i++){
             	String perID=secClient.getPermissionId(perNameArr[i]);
                if(perID == null){
                	throw new BuildException("Specified permission does not exist: "+perNameArr[i]);
                }
                permissionList.add(perID);
            }
        	
            // Assign permissions to service
        	aClient.assignPermToPrincipalForRes(principalID,serviceUuid,permissionList);
    	} catch (Exception e){
    		throw new BuildException("Error assigning permissions to service:"+e.getMessage());
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
     * Set principal name
     * @param principal name
     */
    public void setPrincipalName(String principalName){
    	this.principalName=principalName;
    }

    /**
     * Set domain name
     * @param domain name
     */
    public void setDomainName(String domainName){
    	this.domainName=domainName;
    }

    /**
     * Set comma delimited list of permission names
     * @param comma delimited list of permission names ()
     */
    public void setPermissionNames(String permissionNames){
    	this.permissionNames=permissionNames;
    }

    
	
}
