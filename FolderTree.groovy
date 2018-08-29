
 /*  
  * Print folders and subfolders
  * 
  * Configuration:
  *   DEBUG - print debug info
  *   PRINTFOLDERS - print found subfolders in folders
  *   PRINTJOBS- print found subjobs in folders      
  */
  
		import com.cloudbees.hudson.plugins.folder.*


		List<Item> allJobs = Jenkins.instance.getAllItems();

		boolean DEBUG = false;
		boolean PRINTFOLDERS = true;
		boolean PRINTJOBS = false;


		for(int i = 0; i < allJobs.size() ; i++){
			def item = allJobs.get(i);


			if(DEBUG){
				println("all #"+i+": "+item.fullName);

				if(item instanceof Folder){
					println "IS FOLDER!";
				}
			}

			if(item instanceof Folder){
				println ("-" + item.fullName);
				printFolder(item,1,PRINTFOLDERS,PRINTJOBS);
				println();
			}
		}


		void printFolder(Item folder,count,printF,printJ){
			List<Item> subs = folder.getItems().each{
				if (it instanceof Folder){
					if(printF){
						printBrackets(count);
						println(":F: " + it.fullName)
					}
					printFolder(it,count++,printF,printJ);
				}else if(printJ){
					printBrackets(count);
					println(":J: "  + it.fullName)
				}
			}
		}

		void printBrackets(count){
			for(int x = 0; x < count ; x++){
				print("--");
			}
		}
