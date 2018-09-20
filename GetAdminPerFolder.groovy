//taken @ https://github.com/cloudbees/jenkins-scripts/blob/master/auth-project-report.groovy

//https://javadoc.jenkins.io/plugin/matrix-auth/com/cloudbees/hudson/plugins/folder/properties/AuthorizationMatrixProperty.html
//https://javadoc.jenkins.io/plugin/cloudbees-folder/com/cloudbees/hudson/plugins/folder/AbstractFolder.html
//https://javadoc.jenkins.io/hudson/security/Permission.html

def folderName = "FOLDERNAME"

com.cloudbees.hudson.plugins.folder.Folder folder = jenkins.model.Jenkins.instance.getItem(folderName);
def URL = folder.getAbsoluteUrl();

//iterate over properties and find permissions
folder.properties.each { p -> 
    if(p.class.canonicalName == "com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty") {
        //permissions found, get them
		   Map<hudson.security.Permission,Set<String>> gp = p.getGrantedPermissions();
      
       //interested only in admins of folder- permission to modify the given folder
       gp.get(hudson.security.Permission.fromId("hudson.model.Item.Configure")).each{ us ->
         User usr = User.getById(us,false);
         
         if(usr != null){
            def usrMail = usr.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress();
            print(usr.fullName + " <" + usrMail +">;")
         }               
       }
    }
} 