Jenkins.instance.getItemByFullName('jobFullName').builds.findAll { 
  it.number > 10 && it.number < 6140 }.each {
   it.delete() 
}