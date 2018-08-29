   /*
    * Get all users with emails in email list format
    */
		import hudson.model.User

		User.getAll().each { user ->
          usrMail = user.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress();
          usrId = user.getId();
          
			if(usrMail != null && usrId != null){
				print usrId +' <' +usrMail +'>;';
			}
		}
		println ("\n == END == \n")
                              
print "size: " + User.getAll().size