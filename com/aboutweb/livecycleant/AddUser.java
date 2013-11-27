package com.aboutweb.livecycleant;

import com.aboutweb.livecycleant.AbstractTask;
import org.apache.tools.ant.BuildException;
import com.adobe.idp.dsc.clientsdk.ServiceClientFactory;
import com.adobe.livecycle.usermanager.client.DirectoryManagerServiceClient; 
import com.adobe.idp.um.api.infomodel.impl.*;  
import java.util.*; 

/**
 * Add a user
 *
 */
public class AddUser extends AbstractTask {

	private String domain,userId,firstName,lastName,password,email;
	
    /**
     * Add a user
     * @exception BuildException if an error occurs.
     */
    public void execute() throws BuildException {
    	
    	ServiceClientFactory scf=super.getServiceClientFactory();
        //Create an DirectoryManagerServiceClient object 
        DirectoryManagerServiceClient dmClient = new DirectoryManagerServiceClient(scf); 
         
        //Create a User object and populate its attributes  
        UserImpl u = new UserImpl(); 
        u.setDomainName(domain);  
        u.setUserid(userId);  
        u.setCanonicalName(userId);  
        u.setPrincipalType("USER");  
        u.setGivenName(firstName);  
        u.setFamilyName(lastName); 
        u.setEmail(email);
        u.setLocale(Locale.US); 
        u.setTimezone(TimeZone.getDefault()); 
        u.setDisabled(false); 
         
        // Create user
        try{
        	dmClient.createLocalUser(u,password); 
        } catch (Exception e){
        	throw new BuildException("Error creating user:"+e.getMessage());
        }
                     
    }
    
    /**
     * Set domain
     * @param domain
     */
    public void setDomain(String domain){
    	this.domain=domain;
    }	
 
    /**
     * Set userId
     * @param userId
     */
    public void setUserId(String userId){
    	this.userId=userId;
    }	

    /**
     * Set firstName
     * @param firstName
     */
    public void setFirstName(String firstName){
    	this.firstName=firstName;
    }	

    /**
     * Set lastName
     * @param lastName
     */
    public void setLastName(String lastName){
    	this.lastName=lastName;
    }	

    /**
     * Set password
     * @param password
     */
    public void setPassword(String password){
    	this.password=password;
    }	

    /**
     * Set email
     * @param email
     */
    public void setEmail(String email){
    	this.email=email;
    }	

}
