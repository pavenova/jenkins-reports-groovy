/**
 * Script to obtain last login date of users and print them in csv format
 * see https://github.com/cloudbees/jenkins-scripts/blob/master/get-lastlogin-users.groovy 
 */
import java.text.SimpleDateFormat
import org.acegisecurity.*
import jenkins.security.*
import java.util.Date

String startDate = "02/31/2018 00:00";	
String endDate = "08/31/2018 23:59";
String dateFormatPattern = "MM/dd/yyyy HH:mm";

Calendar startDateCal  = parseDate(startDate,dateFormatPattern);
Calendar endDateCal  = parseDate(endDate,dateFormatPattern);
 
int totalUsers =0
int activeUsersCount =0
	

User.getAll().each{ u ->
  def prop = u.getProperty(LastGrantedAuthoritiesProperty)
  def realUser = false
  Calendar lastLoginCal = Calendar.getInstance()
  
  if (prop) {
    realUser=true
    lastLoginCal.setTime(new Date(prop.timestamp))
    totalUsers++;    
  }

  if (realUser && (prop.timestamp != null ) ){
    if(lastLoginCal.before(endDateCal) && lastLoginCal.after(startDateCal)){
      activeUsersCount++;
      
      usrMail = u.getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress();
      //println lastLoginCal.getTime().format(dateFormatPattern)
      println u.getId() + ';' + u.getDisplayName() + ';' + usrMail + ';' +lastLoginCal.getTime().format(dateFormatPattern)  
    }
  }
}

println "--------------------------------"
println "start date: " + startDate
println "end date: " + endDate
println "total users: " + totalUsers
println "active users: " + activeUsersCount
println "--------------------------------"

def parseDate(String dateIn,String pattern){
	SimpleDateFormat sf = new SimpleDateFormat(pattern)

	Calendar referenceDate = Calendar.getInstance()
	Date parsed = sf.parse(dateIn)
	referenceDate.setTime(parsed)

	return referenceDate;
}