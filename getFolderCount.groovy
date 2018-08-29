    /*
     * Obtain all jobs and count number of folders
     */
    
    import com.cloudbees.hudson.plugins.folder.*
		
		int count = 0;

		Jenkins.instance.getAllItems().each{
		  if(it instanceof Folder){
			count++;
		  }
		  
		}

		print(count);
