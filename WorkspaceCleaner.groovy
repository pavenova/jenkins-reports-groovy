//delete content from workspace folder of disabled jobs

def jobs = Jenkins.instance.getAllItems(Job.class)

for(job in jobs) {
 if(job.disabled){
   
   String workspace = null
   println "found disabled job: " + job.fullName
   
   try{
     workspace = job.workspace
   }catch(Exception e){
     workspace = null
   }
   
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