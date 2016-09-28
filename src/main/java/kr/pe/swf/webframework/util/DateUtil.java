package kr.pe.swf.webframework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	static SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static String getToday ()
	{
		StringBuffer name = new StringBuffer (20) ;
		Calendar c = Calendar.getInstance () ;
		String year = Integer.toString(c.get(Calendar.YEAR)) ;
		String month = Integer.toString(c.get(Calendar.MONTH)+1);
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		if (month.length()< 2) month = "0" + month ;
		if (day.length() < 2) day = "0" + day ;
		name = name.append (year).append(month).append (day) ;
		return name.toString() ;
	}	
	public static String getCurrentTime()
	{
		if (formatter==null) formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
		Date currentTime = new Date();
		String sTime = formatter.format(currentTime) ;
		
		while(true) {	
			
			if( sTime.length() == 23 && sTime.charAt(19)=='.' 
			    && sTime.charAt(16)==':' && sTime.charAt(13)==':'
			    && sTime.charAt(7)=='-' && sTime.charAt(4)=='-' ) break ;
			sTime = formatter.format(currentTime) ;
		}
		
		return sTime ;
	}
	public static String getDateFormat(String sformat) 
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat (sformat);
		String sTime = formatter.format(currentTime) ;
		return sTime;
	}
	public  String add(String cdate,int idiff)
	{
		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(cdate.substring(0, 4)), Integer.parseInt(cdate.substring(4, 6))-1, Integer.parseInt(cdate.substring(6)));
		c.add(Calendar.DAY_OF_MONTH	,idiff);
		String date =getDateFormat(c.getTime(),"yyyyMMdd");
		System.out.println(getDateFormat(c.getTime(),"yyyyMMdd"));
		
		return date;
	}
	public  String getDateFormat(Date currentTime,String sformat)
	{
		SimpleDateFormat formatter = new SimpleDateFormat (sformat);
		String sTime = formatter.format(currentTime) ;
		return sTime;
	}
	public static String newTimestamp() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 9);
        Date dat = cal.getTime();
        String dateTime = formatter.format(dat);
        return dateTime;
	}
	public static String newTimestamp(Date datetime) {
		// TODO Auto-generated method stub
		return null;
	}
	public static Date convertUTCStringToDate(String timeToLive) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String getWeekFirstDay(String sdate)
	{
		Calendar cal = Calendar.getInstance();
		int yy =Integer.parseInt(sdate.substring(0, 4));
		int mm =Integer.parseInt(sdate.substring(4, 6))-1;
		int dd =Integer.parseInt(sdate.substring(6, 8));
		cal.set(yy, mm,dd);
		int yoil = cal.get(cal.DAY_OF_WEEK); 
		int syoil=dd-yoil+1;
		String startDay =sdate.substring(0,6)+(syoil<10?"0"+syoil:syoil);
		return startDay;
	}
	public static String getWeekLastDay(String sdate)
	{
		Calendar cal = Calendar.getInstance();
		int yy =Integer.parseInt(sdate.substring(0, 4));
		int mm =Integer.parseInt(sdate.substring(4, 6))-1;
		int dd =Integer.parseInt(sdate.substring(6, 8));
		cal.set(yy, mm,dd);
		int yoil = cal.get(cal.DAY_OF_WEEK); 
		
		int eyoil =dd+(7-yoil);
		int emonday =getLastDayInMonth(sdate);
		if (eyoil>emonday) eyoil =emonday;
		String startDay =sdate.substring(0,6)+(eyoil<10?"0"+eyoil:eyoil);
		return startDay;
	}
	public static int getLastDayInMonth(String sdate){
		Calendar cal = Calendar.getInstance();
		int yy =Integer.parseInt(sdate.substring(0, 4));
		int mm =Integer.parseInt(sdate.substring(4, 6))-1;
		int dd =Integer.parseInt(sdate.substring(6, 8));
		cal.set(yy, mm,dd);
		return cal.getActualMaximum(Calendar.DATE);
	}
}
