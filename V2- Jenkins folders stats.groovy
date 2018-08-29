import java.text.SimpleDateFormat
import com.cloudbees.hudson.plugins.folder.*
import groovyjarjarasm.asm.Item


		String startDateFolders = "07/01/2018 00:00";

		String endDate = "07/31/2018 23:59";

		String dateFormatPattern = "MM/dd/yyyy HH:mm";

		Calendar startDateFodlersCal  = parseDate(startDateFolders,dateFormatPattern);
		Calendar endDateCal  = parseDate(endDate,dateFormatPattern);

		getTotalNumberOfFolders();
		getNumberOfActiveFolders(startDateFodlersCal,endDateCal);
 

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