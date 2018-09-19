//taken @ https://github.com/cloudbees/jenkins-scripts/blob/master/auth-project-report.groovy

//https://javadoc.jenkins.io/plugin/matrix-auth/com/cloudbees/hudson/plugins/folder/properties/AuthorizationMatrixProperty.html
//https://javadoc.jenkins.io/plugin/cloudbees-folder/com/cloudbees/hudson/plugins/folder/AbstractFolder.html
//https://javadoc.jenkins.io/hudson/security/Permission.html

def folder
def URL
List<String> adminUserList=new ArrayList<String>() 

//get items
for (item in Jenkins.instance.allItems) {

  //find folders
  if (item.class.canonicalName == "com.cloudbees.hudson.plugins.folder.Folder") {
	    folder = item.fullName
	    URL = item.getAbsoluteUrl()
	
	    //iterate over properties and find permissions
	    item.properties.each { p -> 
		      if(p.class.canonicalName == "com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty") {
			          //permissions found, get them
					  Map<hudson.security.Permission,Set<String>> gp = p.getGrantedPermissions()
		        
			         //interested only in admins of folder- permission to modify the given folder
			         gp.get(hudson.security.Permission.fromId("hudson.model.Item.Configure")).each{
			           //getUserList
			           
			           		User usr = User.getById(it,false)
				            //println usr.fullName
					   	 	
							   if(usr != null && usr.fullName.contains("external")) {
								   def usrMail = usr.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress();
								   adminUserList.add(it + ";" + usr.fullName +";" + usrMail)
   							   }
			        }
				 
		      }
	    } 
 
  }
  
  if(adminUserList.size() > 0) {
	  adminUserList.each{user ->
		  println folder + ";" + URL + ";"+ user
	  }
  }
  adminUserList=new ArrayList<String>()
   
}
 