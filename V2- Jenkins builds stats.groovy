import java.text.SimpleDateFormat
import com.cloudbees.hudson.plugins.folder.*
import groovyjarjarasm.asm.Item

    String startDate = "07/01/2018 00:00";	
		String endDate = "07/31/2018 23:59";

		String dateFormatPattern = "MM/dd/yyyy HH:mm";

		Calendar startDateCal  = parseDate(startDate,dateFormatPattern);
		Calendar endDateCal  = parseDate(endDate,dateFormatPattern);
    
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

	def parseDate(String dateIn,String pattern){
		SimpleDateFormat sf = new SimpleDateFormat(pattern);

		Calendar referenceDate = Calendar.getInstance();
		Date parsed = sf.parse(dateIn);
		referenceDate.setTime(parsed);

		return referenceDate;
	}