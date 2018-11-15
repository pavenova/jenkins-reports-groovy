# jenkins-reports-groovy
Groovy script for jenkins to load data about the folders, builds, etc.

Scripts should be used in jenkins script console 

# content 
- DeleteOldBuilds.groovy - will delete the builds from jenkins between the given interval
- DisableJobsInFolder.txt - will disable all jobs in the given folder
- FolderTree.groovy - will print folders and subfolders
- GetFolderPermissions.groovy - will print setted permissions per Folders
- GetPluginsInstalled.groovy - will print installed plugins with versions
- GetUserIdsMailList.groovy - will print mailling list with user IDs 
- GetUsers.groovy - will print mail addresses , mailing list
- JobsActiveInTimeFrame.groovy- print of active jobs stats, recent builds in the interval
- JobsBuildsInInterval.groovy- print of active jobs stats, recent builds in the interval
- KillZombieJob.txt- will kill the job, in case this job stucks
- V2- GetUsersLastLogged.groovy- will print the user id and last login in csv format
- V2- Jenkins builds stats.groovy- print stats about the builds in the given period
- V2- Jenkins folders stats.groovy- print stats about the folders in the given period
- V2- billing, getUsers by folder and pattern.groovy- will print folder§s admin users
- GetAdminPerFolder.groovy- based on the folder name, will printout the mailling lsit users with admin permissions
- getJobBuildsCountCSV.groovy - will print the list of the jobs and number of builds
- WorkspaceCleaner.groovy - will delete data in workspace of disalbed jobs
- WorkspaceCleaner by period.groovy- cleaning workspace based on the last build date
