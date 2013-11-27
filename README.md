livecycle-ant
=============

<b>LiveCycle Ant Tasks</b>

Adobe introduced Ant tasks with the release of LiveCycle ES4.  While these tasks are a welcome addition and help automate LiveCycle deployments, they are very limited in scope.  The tasks provided by Adobe only allow to you import/export archives and deploy/undeploy applications.  Deployments typically require addition settings which are not provided with these tasks, so I started working on a set of tasks to automate the deployment of a project I was working on and decided to release these tasks as open source.  Some of these tasks have very specific functionality I needed for my project and there are some other basic tasks that are still missing.  I’m planning to add more tasks over time starting with the more common configurations you need for deployments like adding users and setting permissions on processes.

I have tested these tasks with LiveCycle ES3 and ES4.  I’m using the same configuration file format Adobe uses with their tasks.  I’ve included the complied jar file, sample build file, and configuration file in the repository.  If you are just interested in using the tasks the jar file is all you really need.

<b>Properties File</b>

The properties file is used by all the tasks and includes connection information for the LiveCycle server including: serverUrl, username, password, and timeout.  

serverUrl-URL to server including the part<br>
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
<pre><code>
&lt;AddUser 
        domain="DefaultDom" 
        userId="test" 
        password="password"
        firstName="Test"
        lastName="Test"
        email="test@test.com"
        propertiesFile="lcserver.properties" /&gt;
</code></pre>
