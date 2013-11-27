livecycle-ant
=============

<b>LiveCycle Ant Tasks</b>

Adobe introduced Ant tasks with the release of LiveCycle ES4.  While these tasks are a welcome addition and help automate LiveCycle deployments, they are very limited in scope.  The tasks provided by Adobe only allow to you import/export archives and deploy/undeploy applications.  Deployments typically require addition settings which are not provided with these tasks, so I started working on a set of tasks to automate the deployment of a project I was working on and decided to release these tasks as open source.  Some of these tasks have very specific functionality I needed for my project and there are some other basic tasks that are still missing.  I’m planning to add more tasks over time starting with the more common configurations you need for deployments like adding users and setting permissions on processes.

I have tested these tasks with LiveCycle ES3 and ES4.  I’m using the same configuration file format Adobe uses with their tasks.  I’ve included the complied jar file, sample build file, and configuration file in the repository.  If you are just interested in using the tasks the jar file is all you really need.


Tasks:
AddUser
Creates a new LiveCycle user account.

Attributes:
domain-Required, LiveCycle domain to create account in
userId-Required, User account id/login
password-Required, at least 8 characters
firstName-Optional, defaults to blank
lastName-Optional, defaults to blank
email-Optional, defaults to blank
propertiesFile-Path to properties file

Example:
<code>
<AddUser 
        domain="DefaultDom" 
        userId="test" 
        password="password"
        firstName="Test"
        lastName="Test"
        email="test@test.com"
        propertiesFile="lcserver.properties" />
</code>
