livecycle-ant
=============

<b>LiveCycle Ant Tasks</b>

Adobe introduced Ant tasks with the release of LiveCycle ES4.  While these tasks are a welcome addition and help automate LiveCycle deployments, they are very limited in scope.  The tasks provided by Adobe only allow to you import/export archives and deploy/undeploy applications.  Deployments typically require addition settings which are not provided with these tasks, so I started working on a set of tasks to automate the deployment of a project I was working on and decided to release these tasks as open source.  Some of these tasks have very specific functionality I needed for my project and there are some other basic tasks that are still missing.  I’m planning to add more tasks over time starting with the more common configurations you need for deployments like adding users and setting permissions on processes.

I have tested these tasks with LiveCycle ES3 and ES4.  I’m using the same configuration file format Adobe uses with their tasks.  I’ve included the complied jar file, sample build file, and configuration file in the repository.  If you are just interested in using the tasks the jar file is all you really need.

<b>Using the Tasks</b>

In order to use the tags several jar files need to be included from the LiveCycle SDK.  The LiveCycle SDK can be found under the LiveCycle install directory.  In addition, each of the tags has to be defined for Ant.  This definition includes a reference to the LiveCycleTasks.jar where the task is defined and classes it depends on from the LiveCycle SDK.   

<b>Example:</b><br>
<pre><code><!-- LiveCycle SDK path -->
&lt;property name="lc.sdk.home" 
    value="F:/Adobe/Adobe LiveCycle ES4/sdk" /&gt;

<!-- Define classpath and libraries for dependencies -->
&lt;path id="application.client.classpath"&gt;
  &lt;pathelement 
    location="${lc.sdk.home}/client-libs/thirdparty/axis.jar"/&gt; 
  &lt;pathelement 
    location="${lc.sdk.home}/client-libs/thirdparty/saaj.jar"/&gt;
  &lt;pathelement 
    location="${lc.sdk.home}/client-libs/thirdparty/wsdl4j.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/thirdparty/commons-logging.jar"/&gt;
  &lt;pathelement 
    location="${lc.sdk.home}/client-libs/thirdparty/jaxrpc.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/thirdparty/commons-discovery.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/thirdparty/commons-collections-3.1.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/thirdparty/activation.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/thirdparty/mail.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/common/adobe-livecycle-client.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/common/adobe-usermanager-client.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/common/adobe-repository-client.jar"/&gt;
  &lt;pathelement location=
    "${lc.sdk.home}/client-libs/common/adobe-application-remote-client.jar"/&gt;  
&lt;/path&gt;

&lt;fileset id="runtime.libs" dir="."&gt;
  &lt;include name="LiveCycleAntTasks.jar"/&gt;
&lt;/fileset&gt;

&lt;path id="runtime.classpath"&gt;
  &lt;fileset refid="runtime.libs"/&gt;
&lt;/path&gt;

&lt;taskdef name="LCImportArchive" classname="com.aboutweb.livecycleant.ImportArchive"&gt;
  &lt;classpath refid="runtime.classpath"/&gt;
  &lt;classpath refid="application.client.classpath"/&gt;
&lt;/taskdef&gt;
</code></pre>



<b>Properties File</b>

The properties file is used by all the tasks and includes connection information for the LiveCycle server including: serverUrl, username, password, and timeout.  

serverUrl-URL to server including the port<br>
username-Administrator or LiveCycle user account with permission to make changes<br>
password-Password for user account<br>
timeout-Connection time out in milliseconds<br>

Example:<br>
<pre><code>serverUrl=http://127.0.0.1:8080
username=administrator
password=password
timeout=1200000
</code></pre>

<b>Tasks</b>

<i>AddUser</i><br>
Creates a new LiveCycle user account.<br>

Attributes:<br>
domain-Required, LiveCycle domain in which to create account<br>
userId-Required, User account id/login<br>
password-Required, at least 8 characters<br>
firstName-Optional, defaults to blank<br>
lastName-Optional, defaults to blank<br>
email-Optional, defaults to blank<br>
propertiesFile-Path to properties file<br>

Example:<br>
<pre><code>&lt;AddUser 
        domain="DefaultDom" 
        userId="test" 
        password="password"
        firstName="Test"
        lastName="Test"
        email="test@test.com"
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>AssignUserRole</i><br>
Assigns one or more roles to a user account.

Attributes:<br>
userId-Required, User account id/login<br>
roles-Required, Comma delimited list of user role names<br>
propertiesFile-Path to properties file<br>

Example:<br>
<pre><code>&lt;AssignUserRole 
        userId="test" 
        roles="LiveCycle Workspace User,Services User"
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>DeployApplication</i><br>
Deploy an existing application on the server

Attributes:<br>
applicationName-Required, Application Name<br>
version-Required, Version of application to deploy<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;Deploy 
        applicationName="Test" 
        version="1.0" 
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>EnableEndpoint</i><br>
Enable endpoint type for service; create it if it does not exist

Attributes:<br>
serviceName - Required, Name of service<br>
serviceType -Required, Service Type (SOAP|REST|EJB|Remoting)<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;EnableEndpoint 
        serviceName="Test/Test" 
        serviceType="SOAP"  
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>ImportArchive</i><br>
Import an archive (lca) file

Attributes:<br>
file- Required, Full path to lca file<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;ImportArchive file="f:\\temp\\Test.lca" 
	    propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>RuntimeConfig</i><br>
Apply saved runtime configuration to application

Attributes:<br>
file- Required, Application name<br>
majorVersion- Required, Major application version<br>
minorVersion- Required, Minor application version<br>
runtimeConfigFile- Required, Full path to runtime configuration file<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;RuntimeConfig 
	    applicationName="Test" 
	    majorVersion="1" 
	    minorVersion="0" 
	    runtimeConfigFile="f:\\temp\\Test-1.0_config.xml" 
	    propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>ServiceMaster</i><br>
Ensure only the specified version of the service is running.  Starts specified version if is not running, stops all other versions of the service

Attributes:<br>
serviceName - Required, Service name<br>
majorVersion- Required, Major service version<br>
minorVersion- Required, Minor service version<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;ServiceMaster 
        serviceName="Test/Test" 
        majorVersion="1"
        minorVersion="0"  
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>ServicePermission</i><br>
Applies one or more permissions for specified user or group to service

Attributes:<br>
serviceName - Required, Service name<br>
domainName - Domain that user or group belongs to<br>
principalName- Required, Name of user or group<br>
permissionName-Required, Comma delimited list of LiveCycle permissions<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;ServicePermission 
        serviceName="Test/Test" 
        domainName="DefaultDom" 
        principalName="test" 
        permissionNames="INVOKE_PERM,READ_PERM"  
        propertiesFile="lcserver.properties" /&gt;
</code></pre>

<i>ServiceStatus</i><br>
Retrieves the status of a service, including component version, major version, minor version, and running status.  Stores the result as a string in the specified Ant variable.  

Attributes:<br>
serviceName - Required, Service name<br>
result - Required, Ant variable name<br>
propertiesFile- Required, Path to properties file<br>

Example:<br>
<pre><code>&lt;ServiceStatus 
	    serviceName="Test/Test" 
	    result="service.result" 
	    propertiesFile="lcserver.properties" /&gt;
	    
service.result= componentVersion=10.0.2.20120224.1.313570;majorVersion=1;minorVersion=0;running=true

</code></pre>
