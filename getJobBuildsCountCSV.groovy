int buildsTotal = 0

for(jobRoot in Jenkins.instance.getAllItems()) {
   int buildCount = 0
  if(jobRoot instanceof Job && jobRoot.getLastBuild() != null) {
    
    
    def jobInst = Jenkins.instance.getItemByFullName(jobRoot.fullName)
    def jBuilds = jobInst.builds
    //println "--builds: " + jBuilds
    
   
    jBuilds.each { build ->
      buildCount++
    }
    
  }
  
  println jobRoot.fullName +";"+count

}
	