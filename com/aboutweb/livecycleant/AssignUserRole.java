package com.aboutweb.livecycleant;

import java.util.ArrayList;
import java.util.List;

import com.aboutweb.livecycleant.AbstractTask;
import org.apache.tools.ant.BuildException;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.idp.um.api.infomodel.*; 
import com.adobe.livecycle.usermanager.client.AuthorizationManagerServiceClient; 
import com.adobe.livecycle.usermanager.client.DirectoryManagerServiceClient; 

/**
 * Assing roles to user
 *
 */
public class AssignUserRole extends AbstractTask {

	private String userId,roles;
	
    /**
     * Assing roles to user
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	
    	ServiceClientFactory scf=super.getServiceClientFactory();
    	// Create an AuthorizationManagerServiceClient object 
        AuthorizationManagerServiceClient amClient = new AuthorizationManagerServiceClient(scf); 

        // Retrieve a principal 
        DirectoryManagerServiceClient dirClient = new DirectoryManagerServiceClient(scf); 
        PrincipalSearchFilter psf = new PrincipalSearchFilter(); 
        psf.setUserId(userId); 
        List<User> principalList;
        try {
        	principalList = dirClient.findPrincipals(psf);  
    	} catch (Exception e){
    		throw new BuildException("Error looking up user: "+e.getMessage());
    	}
      
        // Search filter does a substring matching, so find the exact match 
        String[] oids=new String[1];
        for(int i=0;i<principalList.size();i++){
        	if(principalList.get(i).getUserid().equalsIgnoreCase(userId))
        		oids[0]=principalList.get(i).getOid();
        }

        RoleSearchFilter rsf = new RoleSearchFilter(); 
    	String[] rolesArr = roles.split(",");
        List<String> permissionList=new ArrayList<String>();
        for(int i=0;i<rolesArr.length;i++){
        	List<Role> roleList;
        	rsf.setRoleName(rolesArr[i]); 
        	try {
        		roleList = amClient.findRoles(rsf);
        	} catch (Exception e){
        		throw new BuildException("Error looking up role: "+e.getMessage());
        	}
        	if(roleList.size() == 0){
            	throw new BuildException("Specified permission does not exist: "+permissionList.get(i));
            }
        	try {
        		amClient.assignRole(roleList.get(0).getId(), oids);
        		log("Assigned role:"+roleList.get(0).getName());
        	} catch (Exception e){
        		throw new BuildException("Error assiging role: "+e.getMessage());
        	}
        }                     
    }
    

    /**
     * Set userId
     * @param userId
     */
    public void setUserId(String userId){
    	this.userId=userId;
    }	

    /**
     * Set roles
     * @param comma delimited list of roles
     */
    public void setRoles(String roles){
    	this.roles=roles;
    }	



}
