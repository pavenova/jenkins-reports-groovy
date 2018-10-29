Jenkins.instance.getItemByFullName('jobFullName').builds.findAll { 
  it.number > 0 && it.number < 6140 }.each {
  //locked job
    if(it.isKeepLog() != true){
      it.delete()
    }
}