import java.text.SimpleDateFormat
import com.cloudbees.hudson.plugins.folder.*
import groovyjarjarasm.asm.Item

String startDate = "11/01/2017 00:00";
		String startDateFolders = "6/01/2017 00:00";

		String endDate = "11/30/2017 23:59";

		String dateFormatPattern = "MM/dd/yyyy HH:mm";

		Calendar startDateCal  = parseDate(startDate,dateFormatPattern);
		Calendar startDateFodlersCal  = parseDate(startDateFolders,dateFormatPattern);
		Calendar endDateCal  = parseDate(endDate,dateFormatPattern);

		getTotalNumberOfFolders();
		getNumberOfActiveFolders(startDateFodlersCal,endDateCal);
		getTotalBuildsCount();
		getActiveBuildsCount(startDateCal,endDateCal);


	def getActiveBuildsCount(Calendar startRef,Calendar endRef) {
		int count =0;
		
		for(jobItm in Jenkins.instance.getAllItems()) {
			if(jobItm instanceof Job && jobItm.getLastBuild() != null) {
				
				for(build in jobItm.builds) {
					
					Date buildDate = build.getTime();
					Calendar buildCal = Calendar.getInstance();
					buildCal.setTime(buildDate);
					
					if(buildCal.before(endRef) && buildCal.after(startRef)) {
						count++;
					}
					
				}
			}
		}
		
		println("Number of active builds: " + count);
			
		
	}


	def getTotalBuildsCount(){
		int count = 0;
		
		for(jobRoot in Jenkins.instance.getAllItems()) {
			if(jobRoot instanceof Job && jobRoot.getLastBuild() != null) {
				count += jobRoot.getLastBuild().number;
			}
		}
		
		println("Total builds: " + count);
	}

	def getTotalNumberOfFolders(){
		int count = 0;

		Jenkins.instance.getAllItems().each{
			if(it instanceof Folder){
				count++;
			}
		}

		println("Total folders: " + count);
	}

	def getNumberOfActiveFolders(Calendar startRef,Calendar endRef){
		int count = 0;
	 
		//root items
		for(jobRoot in Jenkins.instance.getAllItems()) {
			if(jobRoot instanceof Folder) {
				boolean buildInFolderFound= false;

				//jobs in folder
				for(subjob in jobRoot.getItems()) {

					if(subjob instanceof Job && subjob.getLastBuild() != null) {
						//found subjob with builds
						for(build in subjob.builds) {
							//get build date and compare, 1 is enough
							Date buildDate = build.getTime();
							Calendar buildCal = Calendar.getInstance();
							buildCal.setTime(buildDate);
							
							if(buildCal.before(endRef) && buildCal.after(startRef)) {
								count++;
								buildInFolderFound = true;
								break;
							}
						}
					}

					if(buildInFolderFound) {
						break;
					}
				}
			}
		}

		println("Active Folders: " + count);

	}

	def parseDate(String dateIn,String pattern){
		SimpleDateFormat sf = new SimpleDateFormat(pattern);

		Calendar referenceDate = Calendar.getInstance();
		Date parsed = sf.parse(dateIn);
		referenceDate.setTime(parsed);

		return referenceDate;
	}

