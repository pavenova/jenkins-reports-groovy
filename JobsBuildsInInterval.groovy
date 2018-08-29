		/*
		 * Jenkins Groovy script for obtain statistics about usage per given period
		 * - in sense of the builds	
		 * 
		 * REFMONTHAGO - offset of period to the past in months (be sure to use minus sign) 
		 * REFDAYSAGO - offset of period to the past in days (be sure to use minus sign) 
		 * 
		 * PRINTALLJOBS- print all found jobs;
		 * PRINTALLBUILDS- print all job builds;
		 * 
		 * PRINTSELSUCC- print all successful builds in given period
		 * PRINTSELFAIL-print all failed builds in period
		 */
     
     import groovyjarjarasm.asm.Item

		//init
		int REFMONTHAGO = -1;
		int REFDAYSAGO = 0;

		boolean PRINTALLJOBS = false;
		boolean PRINTALLBUILDS = false;

		boolean PRINTSELSUCC = false;
		boolean PRINTSELFAIL = true;


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
		int bldTotal;
		int bldInPeriod;

		int bldPeriodSuccess;
		int bldPeriodFails;

		for(int i = 0; i < allJobs.size() ; i++){
			def item = allJobs.get(i);

			//if we want to print all jobs, print its name
			if(PRINTALLJOBS && item != null) {
				println("all #"+i+": "+item.fullName);
			}

			try{
				//never built or is folder eg.
				if(item != null && item.getLastBuild() != null){
					bldTotal += item.getLastBuild().number;

					def job = jenkins.model.Jenkins.instance.getItem(item.fullName);
					job.builds.each {
						def build = it;

						Date lastBuildDate = it.getTime();
						itemLastBuildDateCalendar.setTime(lastBuildDate);

						if(PRINTALLBUILDS) {
							println("found build: "+item.fullName+ " build#"+build.number+", @ " + build.getTime().format(dateFormatPattern));
						}

						if(itemLastBuildDateCalendar.after(referenceDate)){
							bldInPeriod++;

							if (it.getResult() == hudson.model.Result.SUCCESS) {
								bldPeriodSuccess++;
								if(PRINTSELSUCC) {
									println("in period success : "+item.fullName+ " build#"+build.number+", @ " + build.getTime().format(dateFormatPattern));
								}
							}else if(it.getResult() == hudson.model.Result.FAILURE) {
								bldPeriodFails++;
								if(PRINTSELFAIL) {
									println("in period failed : "+item.fullName+ " build#"+build.number+", @ " + build.getTime().format(dateFormatPattern));
								}
							}
						}
					}
				}
			}catch(Exception e){
				//groovy.lang.MissingMethodException,...
			}
		}

		if(PRINTALLJOBS || PRINTALLBUILDS || PRINTSELSUCC || PRINTSELFAIL) {
			println();
		}

		println("stats: ");

		//total build stats
		println("builds total: " + bldTotal);
		println("builds in period: " + bldInPeriod);
		double bldPercent = bldInPeriod/bldTotal;
		bldPercent = bldPercent*10000;
		bldPercent = Math.round(bldPercent)/100;
		println("total / in period: " + bldPercent + " %");
		println();

		//in period stats
		println("builds in period: " + bldInPeriod);
		println("builds in period success: " + bldPeriodSuccess);
		println("builds in period fails: " + bldPeriodFails);
		println();

		double bldPeriodPercentSucc = bldPeriodSuccess/bldInPeriod;
		bldPeriodPercentSucc = bldPeriodPercentSucc*10000;
		bldPeriodPercentSucc = Math.round(bldPeriodPercentSucc)/100;
		println("total success in period: " + bldPeriodPercentSucc + " %");
		println();

		double bldPeriodPercentFail = bldPeriodFails/bldInPeriod;
		bldPeriodPercentFail = bldPeriodPercentFail*10000;
		bldPeriodPercentFail = Math.round(bldPeriodPercentFail)/100;
		println("total failed in period: " + bldPeriodPercentFail + " %");
		println();
