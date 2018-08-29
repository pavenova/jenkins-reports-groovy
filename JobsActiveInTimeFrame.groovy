		/*
		 * Jenkins, Groovy script for obtain all jobs, and statistics about usage per given period
		 * 
		 * REFMONTHAGO - offset of period to the past in months (be sure to use minus sign) 
		 * REFDAYSAGO - offset of period to the past in days (be sure to use minus sign) 
		 * 
		 * PRINTALLJOBS - print all found jobs in Jenkins
		 * PRINTACTIVEJOBS - print all found jobs in Jenkins which were build in given frame period
		 * 
		 * PRINTINACTIVEJOBS -  print all found jobs in Jenkins which were build, but not in given frame period
		 * PRINTNOBUILDJOBS - print all found jobs in Jenkins which were not never built
		 */
     import groovyjarjarasm.asm.Item

		//init
		int REFMONTHAGO = -1;
		int REFDAYSAGO = 0;

		boolean PRINTALLJOBS = false;

		boolean PRINTACTIVEJOBS = false;

		boolean PRINTINACTIVEJOBS = true;
		boolean PRINTNOBUILDJOBS = true;

		String dateFormatPattern = "MM/dd/yyyy HH:mm";

		Calendar referenceDate = Calendar.getInstance();
		referenceDate.set(Calendar.HOUR_OF_DAY,23);
		referenceDate.set(Calendar.MINUTE,59);
		referenceDate.set(Calendar.SECOND,59);
		referenceDate.set(Calendar.MILLISECOND,59);

		//set relevant time frame
		referenceDate.add(Calendar.MONTH,REFMONTHAGO);
		referenceDate.add(Calendar.DAY_OF_MONTH,REFDAYSAGO);

		Calendar itemLastBuildDateCalendar = Calendar.getInstance();

		//load all jobs
		List<Item> allJobs = Jenkins.instance.getAllItems();
		//candidate list
		List<Item> jBuildedInRef = new ArrayList<Item>();

		for(int i = 0; i < allJobs.size() ; i++){
			def item = allJobs.get(i);

			//if we want to print all jobs, print its name
			if(PRINTALLJOBS && item != null) {
				println("all #"+i+": "+item.fullName);
			}

			try{
				//never built or is folder eg.
				if(item != null && item.getLastBuild() != null){
					Date lastBuildDate = item.getLastBuild().getTime();
					itemLastBuildDateCalendar.setTime(lastBuildDate);

					if(itemLastBuildDateCalendar.after(referenceDate)){
						jBuildedInRef.add(item);

						if(PRINTACTIVEJOBS) {
							println("active #"+i+": "+ item.fullName +", last build: " + lastBuildDate.format(dateFormatPattern));
						}
					}else {
						if(PRINTINACTIVEJOBS) {
							println("inactive #"+i+": "+ item.fullName +", last build: " + lastBuildDate.format(dateFormatPattern));
						}
					}
				}else if(item != null) {
					if(PRINTNOBUILDJOBS) {
						println("inactive #"+i+": "+ item.fullName +", without build");
					}
				}
			}catch(Exception e){
				//groovy.lang.MissingMethodException,...
			}
		}

		println "JOBS INFO:"
		println "total: \t\t\t" + allJobs.size();
		println "ref. period active: \t" + jBuildedInRef.size();

		double percentage = (jBuildedInRef.size()/allJobs.size())*100;
		percentage = percentage*100;
		percentage = Math.round(percentage);
		percentage = percentage/100;
		println "percentage: \t\t" + percentage +" %";
