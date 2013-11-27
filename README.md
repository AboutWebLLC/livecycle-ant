livecycle-ant
=============

LiveCycle Ant Tasks

Adobe introduced Ant tags with the release of LiveCycle ES4.  While these tags are a welcome addition and help automate LiveCycle deployments, they are very limited in scope.  The tags provided by Adobe only allow to you import/export archives and deploy/undeploy applications.  Deployments typically require addition settings which are not provided with these tags, so I started working on a set of tags to automate the deployment of a project I was working on and decided to release these tags as open source.  Some of these tags have very specific functionality I needed for my project and there are some other basic tags that are still missing.  I’m planning to add more tags over time starting with the more common configurations you need for deployments like adding users and setting permissions on processes.

I have tested these tags with LiveCycle ES3 and ES4.  I’m using the same configuration file format Adobe uses with their tags.  I’ve included the complied jar file, sample build file, and configuration file in the repository.  If you are just interested in using the tags the jar file is all you really need.
