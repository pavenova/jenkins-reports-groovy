   /*
    * Get all users with emails in email list format
    */
		import hudson.model.User
	
		def mailList ="";

		User.getAll().each { user ->
			usrMail = user.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress();
			if(usrMail != null && (usrMail.toLowerCase().contains("@"))){
				mailList= mailList + usrMail.toLowerCase() + ";"
			}
		}
		print mailList
		println ("\n == END == \n")
                              
//print(User.getAll().size);