//cleanup workspace by last build date

def buildDaysPast = -90

def refDate = new Date()
refDate = refDate.plus(buildDaysPast)
println refDate

def jobs = Jenkins.instance.getAllItems(Job.class)

for(job in jobs) {
  println job.name
  if(job.getLastBuild() == null || job instanceof hudson.model.ExternalJob || job instanceof org.jenkinsci.plugins.workflow.job.WorkflowJob){
    continue
  }
  
  
  def lastBuildDate = job.getLastBuild().getTime()
  if(lastBuildDate.before(refDate)){
   
   String workspace = workspace = job.workspace
   println " cleaning candidate, last build: " + lastBuildDate
   println "  workspace: " +  workspace
   println job.absoluteUrl
 
    
   
   if(workspace != null){
     File folder = new File(workspace) 
     
     //Check if the Workspace folder exists
     if(folder!=null && folder.exists()){

       File[] files = new File(workspace).listFiles().sort(){
         //println it.name  
         
         if(!it.isFile()){
           it.deleteDir()
         }else{
           it.delete()
         }
       }
     }  
   }
 } 
}